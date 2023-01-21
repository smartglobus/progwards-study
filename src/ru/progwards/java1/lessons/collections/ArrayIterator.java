package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {

    private T[] array;
    private int i = -1;

    ArrayIterator(T[] array) {
        this.array = array;
    }

    ArrayIterator(T[] array, int i) {
        super();
//        this.array=array;
        this.i = i;
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        if (i >= array.length - 1) return false;
        return true;
    }

    @Override
    public T next() {
        // TODO Auto-generated method stub
        i++;
        return array[i];
    }

    public static void main(String[] args) {
        String[] names = {"Александр","Александр","Григорий","Дмитрий","Дмитрий","Григорий","Александр","Григорий","Дмитрий"};
        ArrayIterator<String> namesIterator = new ArrayIterator<String>(names);
        System.out.println(namesIterator.next());
        System.out.println(namesIterator.next());
        System.out.println(namesIterator.next());
    }
}
/*
Сделать итератор по одномерному массиву, реализовать методы hasNext() и next(). Шаблон пустого итератора:

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
