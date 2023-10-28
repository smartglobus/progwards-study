package ru.progwards.smartglobus;


import java.math.BigDecimal;

class Persona {
    public String name;
    public Persona(String name) {
        this.name = name;
    }
}

abstract class PersonCompare {
    public int compare(Persona p1, Persona p2) {
        return 0;
    }

    public static void main(String[] args) {
        PersonCompare personCompare = new PersonCompare() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return p1.name.compareTo(p2.name);
            }
        };


    }
}

class Rectangle {

    Rectangle(BigDecimal a, BigDecimal b) {
        this.a = a;
        this.b = b;
    }
    public BigDecimal a;
    public BigDecimal b;
    public static boolean rectCompare(Rectangle r1, Rectangle r2){
        return (r1.a.multiply(r1.b)).compareTo((r2.a.multiply(r2.b)))==0;
    }

    public static void main(String[] args) {
        Rectangle r1 = new Rectangle(new BigDecimal("3"),new BigDecimal("5"));
        Rectangle r2 = new Rectangle(new BigDecimal("7.5"),new BigDecimal("2"));
        System.out.println(rectCompare(r1,r2));
    }
}


