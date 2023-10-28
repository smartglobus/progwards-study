package ru.progwards.smartglobus;

class Triangle {

    public int a;
    protected int b;
    private int c;

     public  Triangle(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

//    @Override
//    public String toString() {
//        return a + ":" + b + ":" + c;
//    }

    public static void main(String[] args) {
        Triangle t1 = new Triangle(5, 6, 7);
        System.out.println(t1.a);
        System.out.println(t1.b);
        System.out.println(t1.c);
    }

}

