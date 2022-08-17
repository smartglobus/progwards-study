package ru.progwards.java1.lessons.useclasses;

public class Count {
    int count;

    public Count(){
        count = 0;
    }

    public Count(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void inc() {
        count++;
    }

    public boolean dec() {
        count--;
        if (count <= 0)
            return true;
        return false;
    }

    public static void main(String[] args) {

        Count testZero = new Count(10);
        Count byDef = new Count();
//        System.out.println(byDef.getCount());
//        while (!testZero.dec()){}
//            System.out.println("count равен 0");
        // Так лаконичнее, но, при том же результате, очерёдность вывода "count равен 0" и выхода из цикла меняется.

        while (true) {
            if (testZero.dec()) {
                System.out.println("count равен 0");
                break;
            }
        }
    }
}
