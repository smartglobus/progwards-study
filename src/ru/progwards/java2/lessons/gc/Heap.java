package ru.progwards.java2.lessons.gc;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

// Имеется массив байт, который будет представлять из себя кучу - heap.
// Нужно будет написать алгоритм, который выделяет и освобождает память (ячейки в массиве) и делает дефрагментацию.
public class Heap {
    private byte[] bytes;
    //    private Map<Integer, Integer> freeReg = new HashMap<>();
    private Map<Integer, Integer> ocupReg = new HashMap<>();//
//    private Map<Integer, MemBlock> ocuReg = new HashMap<>();// ????????
    private List<MemBlock> freeReg = new ArrayList<>();//Comparator.comparing(o -> o.pos));
    //    private List<MemBlock> ocupReg = new ArrayList<>();//Comparator.comparing(o -> o.pos));
//    private TreeSet<MemBlock> ocupReg = new TreeSet<>(Comparator.comparing(o -> o.pos));
    private byte label = 1;

    Heap(int maxHeapSize) {
        bytes = new byte[maxHeapSize];
//        freeReg.put(0, maxHeapSize);
        freeReg.add(new MemBlock(0, maxHeapSize));
    }

    private static class MemBlock {//implements Comparable<MemBlock> {
        int pos;
        int size;

        public MemBlock(int pos, int size) {
            this.pos = pos;
            this.size = size;
        }

//        @Override
//        public int compareTo(MemBlock o) {
//            return Integer.compare(pos, o.pos);
//        }
    }


    // метод "размещает", т.е. помечает как занятый блок памяти с количеством ячеек массива heap равным size.
    // Соответственно это должен быть непрерывный блок (последовательность ячеек), которые на момент выделения свободны.
    // Возвращает "указатель" - индекс первой ячейки в массиве, размещенного блока.
    private boolean tryAfterCompact;
    private int bIndex = 0; // переменная для корректного return после повторного, рекурсивного вызова malloc()

    public int malloc(int size) throws OutOfMemoryException {
//        freeReg.sort(Comparator.comparing(o -> o.pos));
        int n = Math.abs(ThreadLocalRandom.current().nextInt() % 10);//(ocuReg.size() / 100000 + 10));
//        int n = Math.abs(ThreadLocalRandom.current().nextInt()%100);
        if (n == 0) defrag();

        for (MemBlock b : freeReg) {
            if (b.size >= size) {
                tryAfterCompact = false;
//                ocuReg.put(b.pos, new MemBlock(b.pos, size));
                ocupReg.put(b.pos, size);

                for (int i = b.pos; i < b.pos + size; i++)
                    bytes[i] = (byte) (label % 127);// визуализация разницы блоков для наглядности в отладке
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
//        freeReg.sort(Comparator.comparing(o -> o.pos));


//        if (ocuReg.containsKey(ptr)) {
//            MemBlock mb = ocuReg.remove(ptr);
//            freeReg.add(new MemBlock(ptr, mb.size));
//            for (int i = ptr; i < ptr + mb.size; i++) bytes[i] = 0;
//        } else throw new InvalidPointerException("Неверный указатель на блок ptr = " + ptr +
//                "\nРазмер bytes = " + bytes.length + ", freeReg = " + freeReg.size() + ", ocupReg = " + ocuReg.size());


        if (ocupReg.containsKey(ptr)) {
            int mbSize = ocupReg.get(ptr);
            freeReg.add(new MemBlock(ptr, mbSize));
            for (int i = ptr; i < ptr + mbSize; i++) bytes[i] = 0;
            ocupReg.remove(ptr);
        } else throw new InvalidPointerException("Неверный указатель на блок ptr = " + ptr +
                "\nРазмер bytes = " + bytes.length + ", freeReg = " + freeReg.size() + ", ocupReg = " + ocupReg.size());

//        for (MemBlock b : ocupReg) {
//            if (b.pos == ptr) {
//                freeReg.add(new MemBlock(ptr, b.size));
//                for (int i = b.pos; i < b.pos + b.size; i++) bytes[i] = 0;
//                ocupReg.remove(b);
//                return;
//            }
//
//        }
//        throw new InvalidPointerException("Неверный указатель на блок ptr = " + ptr +
//                "\nРазмер bytes = " + bytes.length + ", freeReg = " + freeReg.size() + ", ocupReg = " + ocupReg.size());
    }

    //Метод осуществляет дефрагментацию кучи - ищет смежные свободные блоки,
    // границы которых соприкасаются и которые можно слить в один.
    public void defrag() {
// ??? если использовать сортировку по размеру, а не по адресам (по индексам), то надо сначала создать копию freeReg с
// сортировкой по размеру, а в конце заменить содержимое freeReg на полученный Set
//        TreeSet<MemBlock> newSorted = new TreeSet<>((Comparator.comparing(o -> o.size)));
//        newSorted.addAll(freeReg);
//        newSorted.stream().forEach(o -> System.out.println("pos:"+o.pos + ", size:"+o.size));
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
//        for (Integer pos : sortedPos) {
//            MemBlock mb = ocuReg.get(pos);
//            for (int i = 0; i < mb.size; i++) bytes[newPos + i] = bytes[mb.pos + i];
//            mb.pos = newPos;
//            ocuReg.remove(pos);
//            ocuReg.put(newPos, mb);
//            newPos += mb.size;
//        }
//        ocupReg.sort(Comparator.comparing(o -> o.pos));
        Iterator<Integer> dataBlockIterator = sortedPos.iterator();

        while (dataBlockIterator.hasNext()) {
            int mbIndex = dataBlockIterator.next();
            int mbSize = ocupReg.get(mbIndex);
            for (int i = 0; i < mbSize; i++) bytes[newPos + i] = bytes[mbIndex + i];
            if (mbIndex!=newPos){
                ocupReg.remove(mbIndex);
                ocupReg.put(newPos, mbSize);
            }
            newPos += mbSize;
        }
        // Решил сделать здесь "радикальную" дефрагментацию freeReg, так как не увидел смысла переносить пустые блоки по
        // отдельности. Тем более, это не спасёт при вынужденном вызове из malloc и приведёт к OutOfMemoryException
        for (int i = newPos; i < bytes.length; i++) bytes[i] = 0;
        freeReg.clear();
        freeReg.add(new MemBlock(newPos, bytes.length - newPos));
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
//0
//5
//7
//10
//16
//21
//28
//31

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
        return msg;//+"Произошло исключение: Неверный указатель.";
    }
}