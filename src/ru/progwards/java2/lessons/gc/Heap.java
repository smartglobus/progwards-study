package ru.progwards.java2.lessons.gc;

import java.util.*;

// Имеется массив байт, который будет представлять из себя кучу - heap.
// Нужно будет написать алгоритм, который выделяет и освобождает память (ячейки в массиве) и делает дефрагментацию.
public class Heap {
    private byte[] bytes;
    //    private Map<Integer, Integer> freeReg = new HashMap<>();
//    private Map<Integer, Integer> ocupReg = new HashMap<>();
    private SortedSet<memBlock> free = new TreeSet<>(Comparator.comparing(o -> o.pos));
    private SortedSet<memBlock> ocup = new TreeSet<>(Comparator.comparing(o -> o.size));

    Heap(int maxHeapSize) {
        bytes = new byte[maxHeapSize];
//        freeReg.put(0, maxHeapSize);
        free.add(new memBlock(0, maxHeapSize));
    }

    private static class memBlock {//implements Comparable<memBlock> {
        int pos;
        int size;

        public memBlock(int pos, int size) {
            this.pos = pos;
            this.size = size;
        }

//        @Override
//        public int compareTo(memBlock o) {
//            return Integer.compare(pos, o.pos);
//        }
    }


    // метод "размещает", т.е. помечает как занятый блок памяти с количеством ячеек массива heap равным size.
    // Соответственно это должен быть непрерывный блок (последовательность ячеек), которые на момент выделения свободны.
    // Возвращает "указатель" - индекс первой ячейки в массиве, размещенного блока.
    private boolean tryAfterCompact;

    public int malloc(int size) throws OutOfMemoryException {

        for (memBlock b : free) {
            if (b.size >= size) {
                ocup.add(new memBlock(b.pos, size));
                for (int i = b.pos; i < b.pos + size; i++) bytes[i] = 1;
                if (b.size == size) {
                    free.remove(b);
                    return b.pos;
                }
                int bInd = b.pos;
                b.pos += size;
                b.size -= size;
                return bInd;
            }
        }

        tryAfterCompact = !tryAfterCompact; // no free space: compact() & second try...

        if (tryAfterCompact) {
            compact();
            malloc(size);
        } else throw new OutOfMemoryException();

        return 0;
    }

    // Метод"удаляет", т.е. помечает как свободный блок памяти по "указателю".
    // Проверять валидность указателя - т.е. то, что он соответствует началу ранее выделенного
    // блока, а не его середине, или вообще, уже свободному.
    public void free(int ptr) throws InvalidPointerException {
        for (memBlock b : ocup) {
            if (b.pos == ptr) {
                free.add(new memBlock(ptr, b.size));
                for (int i = b.pos; i < b.pos + b.size; i++) bytes[i] = 0;
                ocup.remove(b);
                return;
            }
        }
        throw new InvalidPointerException();
    }

    //Метод осуществляет дефрагментацию кучи - ищет смежные свободные блоки,
    // границы которых соприкасаются и которые можно слить в один.
    public void defrag() {

    }

    //Метод компактизация кучи - перенос всех занятых блоков в начало хипа, с копированием самих данных - элементов массива.
    // Для более точной имитации производительности копировать просто в цикле по одному элементу, не используя System.arraycopy.
    // Обязательно запускаем compact из malloc если не нашли блок подходящего размера
    public void compact() {

    }

    public static class OutOfMemoryException extends RuntimeException {
        @Override
        public String getMessage() {
            return "Произошло исключение: Переполнение кучи.";
        }
    }

    public static class InvalidPointerException extends RuntimeException {
        @Override
        public String getMessage() {
            return "Произошло исключение: Неверный указатель.";
        }
    }


    public static void main(String[] args) {
        Heap heap = new Heap(20);
        try {
            System.out.println(heap.malloc(5));
            System.out.println(heap.malloc(4));
            System.out.println(heap.malloc(3));
            heap.free(7);
            System.out.println(heap.malloc(6));
            heap.compact();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
