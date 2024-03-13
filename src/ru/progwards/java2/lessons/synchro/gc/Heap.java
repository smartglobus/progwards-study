package ru.progwards.java2.lessons.synchro.gc;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


// Имеется массив байт, который будет представлять из себя кучу - heap.
// Нужно будет написать алгоритм, который выделяет и освобождает память (ячейки в массиве) и делает дефрагментацию.
public class Heap {
    private byte[] bytes;
    private ConcurrentNavigableMap<Integer, Integer> ocupReg = new ConcurrentSkipListMap<>();// <pos, size>
    private List<MemBlock> freeReg = new CopyOnWriteArrayList<>();
    private Lock freeRegLock = new ReentrantLock();
    private byte label = 1;

    Heap(int maxHeapSize) {
        bytes = new byte[maxHeapSize];
        freeReg.add(new MemBlock(0, maxHeapSize));
        defragDaemon();
    }

    private class MemBlock {
        int pos;
        int size;

        public MemBlock(int pos, int size) {
            this.pos = pos;
            this.size = size;
        }
    }

    Lock malLock = new ReentrantLock();

    // метод "размещает", т.е. помечает как занятый блок памяти с количеством ячеек массива heap равным size.
    // Соответственно это должен быть непрерывный блок (последовательность ячеек), которые на момент выделения свободны.
    // Возвращает "указатель" - индекс первой ячейки в массиве, размещенного блока.
    public int malloc(int size) throws OutOfMemoryException {
//        int n = Math.abs(ThreadLocalRandom.current().nextInt() % 20);

        malLock.lock();
//        if (n == 0) defrag();
        int bIndex = findSpace(size);
        if (bIndex == -1) {
            System.out.println("Вызван compact()");
            compact();
            bIndex = findSpace(size);
        }
        malLock.unlock();

        if (bIndex != -1) return bIndex;
        else throw new OutOfMemoryException();
    }

    private int findSpace(int size) {
        for (MemBlock b : freeReg) {
            if (b.size >= size) {
                int bIndex = b.pos;
                pushMB(b, size);
                return bIndex;
            }
        }
        return -1;
    }

    private void pushMB(MemBlock b, int size) {
        ocupReg.put(b.pos, size);

        for (int i = b.pos; i < b.pos + size; i++)
            bytes[i] = label;// визуализация разницы блоков для наглядности в отладке
        label++;
        freeRegLock.lock();
        if (b.size == size) {
            freeReg.remove(b);
        } else {
            b.pos += size;
            b.size -= size;
        }
        freeRegLock.unlock();
    }

    // Метод "удаляет", т.е. помечает как свободный блок памяти по "указателю".
    // Проверять валидность указателя - т.е. то, что он соответствует началу ранее выделенного
    // блока, а не его середине, или вообще, уже свободному.
    List<Integer> pointers = new CopyOnWriteArrayList<>();// отладка. Список актуальных указателей на размещенные блоки

    public void free(int ptr) throws InvalidPointerException {

        try {
            freeRegLock.lock();
            if (ocupReg.containsKey(ptr)) {
                int mbSize = ocupReg.remove(ptr);
                freeReg.add(new MemBlock(ptr, mbSize));
                for (int i = ptr; i < ptr + mbSize; i++) bytes[i] = 0;
            } else {
                throw new InvalidPointerException("\nНеверный указатель на блок ptr = " + ptr + "  " + Thread.currentThread().getName()
                        + "    Размер bytes = " + bytes.length + ", freeReg = " + freeReg.size() + ", ocupReg = " + ocupReg.size()
                        + ", pointers = " + pointers.size());
            }
        } finally {
            freeRegLock.unlock();
        }
    }


    //Метод осуществляет дефрагментацию кучи - ищет смежные свободные блоки,
    // границы которых соприкасаются и которые можно слить в один.
    public void defrag() {
        freeReg.sort(Comparator.comparing(o -> o.pos));

        for (int i = 1; i < freeReg.size(); i++) {
            if (freeReg.get(i - 1).pos + freeReg.get(i - 1).size == freeReg.get(i).pos) {
                freeReg.get(i - 1).size += freeReg.get(i).size;
                freeReg.remove(i);
                i--;
            }
        }
    }

    private void defragDaemon() {

        Defrag defragThread = new Defrag();
//        defragThread.setPriority(3);
        defragThread.setDaemon(true);
        defragThread.start();
    }


    class Defrag extends Thread {

        @Override
        public void run() {

            freeReg.sort(Comparator.comparing(o -> o.pos));

            for (int i = 1; i < freeReg.size(); i++) {
                if (freeRegLock.tryLock()) {
                    if (freeReg.get(i - 1).pos + freeReg.get(i - 1).size == freeReg.get(i).pos) {
                        freeReg.get(i - 1).size += freeReg.get(i).size;
                        freeReg.remove(i);
                        i--;
                    }
                    freeRegLock.unlock();
                }
            }
            defragDaemon();
        }
    }

    //Метод компактизация кучи - перенос всех занятых блоков в начало хипа, с копированием самих данных - элементов массива.
    // Для более точной имитации производительности копировать просто в цикле по одному элементу, не используя System.arraycopy.
    // Обязательно запускаем compact из malloc если не нашли блок подходящего размера
    public synchronized void compact() {

// Обязательно идти по возрастанию pos!!! Иначе можно затереть данные с меньшим индексом (pos), если они были вписаны в ocupReg позже!
        SortedSet<Integer> sortedPos = new TreeSet<>(ocupReg.keySet());
        int newPos = 0;

        for (int mbIndex : sortedPos) {
            int mbSize = ocupReg.get(mbIndex);
            for (int i = 0; i < mbSize; i++) bytes[newPos + i] = bytes[mbIndex + i];
            if (mbIndex != newPos) {
                ocupReg.remove(mbIndex);
                ocupReg.put(newPos, mbSize);
            }
            newPos += mbSize;
        }

        for (int i = newPos; i < bytes.length; i++) bytes[i] = 0;
        freeReg.clear();
        freeReg.add(new MemBlock(newPos, bytes.length - newPos));
    }


    // Не очень понял, как этими методами воспользоваться...
    public void getBytes(int ptr, byte[] bytes) {
// создать массив длиной size, вызвыть этот метод с параметрами ptr в ocupReg  и именем этого массива ?????????
        //System.arraycopy(this.bytes, ptr, bytes, 0, size);
    }

    public void setBytes(int ptr, byte[] bytes) {
        //System.arraycopy(bytes, 0, this.bytes, ptr, size);
    }


    Lock runLock = new ReentrantLock();

    public static void main(String[] args) {

        while (true) {
            Heap heap = new Heap(13000);

            List<HeapTestThread> threads = new ArrayList<>();

            long startTime = System.currentTimeMillis();
            try {
                for (int i = 0; i < 500; i++) {
                    HeapTestThread hTst = new HeapTestThread(heap);
                    threads.add(hTst);
                    hTst.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (HeapTestThread h : threads) {
                try {
                    h.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("\nВремя: " + (System.currentTimeMillis() - startTime));
        }
    }
}


class OutOfMemoryException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Переполнение кучи.";
    }
}

class InvalidPointerException extends RuntimeException {
    private String msg;

    InvalidPointerException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}

class HeapTestThread extends Thread {
    Heap heap;
    volatile List<Integer> pointers; // список актуальных указателей на размещенные блоки

    public HeapTestThread(Heap heap) {
        this.heap = heap;
        this.pointers = heap.pointers;
    }

    @Override
    public void run() throws InvalidPointerException {

        try {
            Thread.sleep((long) (Math.random() * 10));
            int p1 = heap.malloc(5);
            pointers.add(p1);
            Thread.sleep(1);

            int p2 = heap.malloc(2);
            pointers.add(p2);
            Thread.sleep(1);

            int p3 = heap.malloc(3);
            pointers.add(p3);
            Thread.sleep(1);

            int p4 = heap.malloc(6);
            pointers.add(p4);
            Thread.sleep(1);

            int p5 = heap.malloc(1);
            pointers.add(p5);
            Thread.sleep(1);

            int p6 = heap.malloc(7);
            pointers.add(p6);
            Thread.sleep(1);

            int p7 = heap.malloc(3);
            pointers.add(p7);
            Thread.sleep(1);

            int p8 = heap.malloc(4);
            pointers.add(p8);


            heap.runLock.lock();
            heap.free(pointers.remove((int) (Math.random() * 2)));
            heap.runLock.unlock();

            heap.runLock.lock();
            heap.free(pointers.remove((int) (Math.random() * 2)));
            heap.runLock.unlock();


            heap.runLock.lock();
            heap.free(pointers.remove((int) (Math.random() * 2)));
            heap.runLock.unlock();

            heap.runLock.lock();
            heap.free(pointers.remove((int) (Math.random() * 2)));
            heap.runLock.unlock();

        } catch (InvalidPointerException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

