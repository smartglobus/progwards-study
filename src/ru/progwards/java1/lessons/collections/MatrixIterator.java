package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MatrixIterator<T> implements Iterator<T> {

    private T[][] array;
//    private T[] array1D;
    private List<T> arr1D = new ArrayList<>();


    private int i = -1;

    MatrixIterator(T[][] array) {
        this.array = array;
if (array.length !=0) {
    for (int i = 0; i < array[0].length; i++) {
        for (int j = 0; j < array.length; j++) {
            arr1D.add(array[i][j]);
        }
    }
}
    }

    @Override
    public boolean hasNext() {
        return i < arr1D.size() - 1;
    }

    @Override
    public T next() throws NoSuchElementException {

try{
    i++;
    return arr1D.get(i);
} catch (Exception e) {throw new NoSuchElementException();
//    e.printStackTrace();
}
//        if (this.hasNext()) {
//
//}else {
//
//}

    }

    public static void main(String[] args) {
        String[][] zero = new String[0][0];
        String[][] names = {{"Александр", "Александр", "Григорий"}, {"Дмитрий", "Дмитрий", "Григорий"}, {"Александр", "Григорий", "Дмитрий"}};

        MatrixIterator<String> namesIterator = new MatrixIterator<>(names);
        System.out.println(namesIterator.next());
        System.out.println(namesIterator.next());
        System.out.println(namesIterator.next());
    }
}
/*
Сделать итератор MatrixIterator по двумерному массиву (матрице), который разворачивает матрицу в линейную последовательность построчно: a[0][0], a[0][1], ...a[0][N],a[1][0], a[1][1]...a[1][N]... a[M][N]

Шаблон для итератора взять от ArrayIterator :

	public class ArrayIterator<T> implements Iterator<T> {

		private T[] array;

		ArrayIterator(T[] array) {
			this.array = array;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public T next() {
			// TODO Auto-generated method stub
			return null;
		}
	}


 */
