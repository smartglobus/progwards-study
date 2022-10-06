package ru.progwards.java1.lessons.interfaces2;

public class ArraySort {
    public static void sort(CompareWeight[] a) {

        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i].getWeight() > a[j].getWeight()) {

                    CompareWeight temp = new CompareWeight() {
                        @Override
                        public double getWeight() {
                            return 0;
                        }

                        @Override
                        public CompareResult compareWeight(CompareWeight smthHasWeight) {
                            return null;
                        }
                    };
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }
}
