package ru.progwards.smartglobus;

public class Gun {
    String model;
    Double caliber;
    private int cartrigeCount;

    public Gun(String model, Double caliber, int cartrigeCount) {
        this.model = model;
        this.caliber = caliber;
        this.cartrigeCount = cartrigeCount;
    }

    public void setCartrigeCount(int cartrigeCount) {
        if (cartrigeCount > 16) {
            this.cartrigeCount = 16;
        }
        this.cartrigeCount = cartrigeCount;
    }

    public int getCartrigeCount() {
        return cartrigeCount;
    }


    class Figure {
        String name;
        int num;

//        {
//            String
//        }



        public Figure(String name) {
            this.name = name;

        }

        public Figure() {
            this("фигура");

        }
        class Segment extends Figure {

            Segment(){
                //name="отрезок";
                super("отрезок");
            }
        }

        public String type(){
            return "figa";
        }



    }
    class Square extends Figure{
        @Override
        public String type(){
            return ("doubleFiga");
        }
    }

    class Point2D {
        int x;
        int y;

        Point2D(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString(){
            return x + "," + y;
        }
    }

    class Point3D extends Point2D {
        private int z;

        Point3D(int x, int y, int z){
            super(x,y);
            this.z = z;
        }

        @Override
        public String toString(){
            return super.toString() + "," + z;
        }


    }


    public static void main(String[] args) {
        int[] a1 = {12, 5, 0, 58, 36};
        int[] a2 = a1;// ссылка на один и тот же объект!
        a2[2] = 99;
        System.out.println(java.util.Arrays.equals(a1, a2));//true!!!
    }
    //    Определите сеттер и геттер для свойства cartrigeCount.В сеттере, если в параметре передали более, чем 16, установите в свойство 16.
}
