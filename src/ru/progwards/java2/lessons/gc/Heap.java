package ru.progwards.java2.lessons.gc;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


// Имеется массив байт, который будет представлять из себя кучу - heap.
// Нужно будет написать алгоритм, который выделяет и освобождает память (ячейки в массиве) и делает дефрагментацию.
public class Heap {
    private byte[] bytes;
    private SortedMap<Integer, Integer> ocupReg = new TreeMap<>();
    private List<MemBlock> freeReg = new ArrayList<>();
    private byte label = 1;

    Heap(int maxHeapSize) {
        bytes = new byte[maxHeapSize];
        freeReg.add(new MemBlock(0, maxHeapSize));
    }

    private class MemBlock {
        int pos;
        int size;

        public MemBlock(int pos, int size) {
            this.pos = pos;
            this.size = size;
        }
    }


    // метод "размещает", т.е. помечает как занятый блок памяти с количеством ячеек массива heap равным size.
    // Соответственно это должен быть непрерывный блок (последовательность ячеек), которые на момент выделения свободны.
    // Возвращает "указатель" - индекс первой ячейки в массиве, размещенного блока.
    private boolean tryAfterCompact;
    private int bIndex = 0; // переменная для корректного return после повторного, рекурсивного вызова malloc()

    public int malloc(int size) throws OutOfMemoryException {
        int n = Math.abs(ThreadLocalRandom.current().nextInt() % 15);
        if (n == 0) defrag();

        for (MemBlock b : freeReg) {
            if (b.size >= size) {
                tryAfterCompact = false;
                ocupReg.put(b.pos, size);

                for (int i = b.pos; i < b.pos + size; i++)
                    bytes[i] = label;// визуализация разницы блоков для наглядности в отладке
                label++;

                if (b.size == size) {
                    freeReg.remove(b);
                    bIndex = b.pos;
                    return b.pos;
                }
                bIndex = b.pos;
                b.pos += size;
                b.size -= size;
                return bIndex;
            }
        }

        tryAfterCompact = !tryAfterCompact; // no freeReg space: compact() & вторая попытка malloc(size)...

        if (tryAfterCompact) {
            compact();
            malloc(size);
        } else {
            throw new OutOfMemoryException();
        }

        return bIndex;
    }

    // Метод "удаляет", т.е. помечает как свободный блок памяти по "указателю".
    // Проверять валидность указателя - т.е. то, что он соответствует началу ранее выделенного
    // блока, а не его середине, или вообще, уже свободному.
    public void free(int ptr) throws InvalidPointerException {

        if (ocupReg.containsKey(ptr)) {
            int mbSize = ocupReg.remove(ptr);
            freeReg.add(new MemBlock(ptr, mbSize));
            for (int i = ptr; i < ptr + mbSize; i++) bytes[i] = 0;
        } else throw new InvalidPointerException("Неверный указатель на блок ptr = " + ptr +
                "\nРазмер bytes = " + bytes.length + ", freeReg = " + freeReg.size() + ", ocupReg = " + ocupReg.size());
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

    //Метод компактизация кучи - перенос всех занятых блоков в начало хипа, с копированием самих данных - элементов массива.
    // Для более точной имитации производительности копировать просто в цикле по одному элементу, не используя System.arraycopy.
    // Обязательно запускаем compact из malloc если не нашли блок подходящего размера
    public void compact() {
        System.out.println("Вызван compact()");
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


    public static void main(String[] args) {
        Heap heap = new Heap(40);
        try {
            System.out.println(heap.malloc(5));
            System.out.println(heap.malloc(2));
            System.out.println(heap.malloc(3));
            System.out.println(heap.malloc(6));
            System.out.println(heap.malloc(5));
            System.out.println(heap.malloc(7));
            System.out.println(heap.malloc(3));
            System.out.println(heap.malloc(4));

            heap.free(7);
            heap.free(10);
            heap.free(16);
            heap.free(28);
            System.out.println(heap.malloc(22));

            heap.compact();
            heap.defrag();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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