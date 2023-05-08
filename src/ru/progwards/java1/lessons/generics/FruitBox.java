package ru.progwards.java1.lessons.generics;

import java.util.ArrayList;

public class FruitBox<Fruit> extends ArrayList<Fruit> implements Comparable<FruitBox> {

    @Override
    public boolean add(Fruit f) {
        return super.add(f);
    }

    public float getWeight() {
        float w = 0;
        if (size() > 0 && get(0) instanceof Apple) w = 1.0f;
        if (size() > 0 && get(0) instanceof Orange) w = 1.5f;
        return size() > 0 ? size() * w : 0;
    }

    void moveTo(FruitBox<Fruit> fb) {
        fb.addAll(this);
        clear();
    }

    @Override
    public int compareTo(FruitBox fb) {
        return Float.compare(getWeight(), fb.getWeight());
    }


    public static void main(String[] args) {
        FruitBox<Apple> appleBox1 = new FruitBox<>();
        FruitBox<Apple> appleBox2 = new FruitBox<>();
        FruitBox<Orange> orangeBox1 = new FruitBox<>();

        appleBox1.add(new Apple());
        appleBox1.add(new Apple());
        appleBox2.add(new Apple());
        appleBox2.add(new Apple());
        appleBox2.add(new Apple());
        orangeBox1.add(new Orange());
        orangeBox1.add(new Orange());
        orangeBox1.add(new Orange());

//        orangeBox1.moveTo(appleBox1);

        System.out.println("orangeBox1 " + orangeBox1.getWeight() + "; appleBox1 " + appleBox1.getWeight() + "; appleBox2 " + appleBox2.getWeight());
        System.out.println(appleBox1.compareTo(orangeBox1));
        appleBox2.moveTo(appleBox1);
        System.out.println("orangeBox1 " + orangeBox1.getWeight() + "; appleBox1 " + appleBox1.getWeight() + "; appleBox2 " + appleBox2.getWeight());
        System.out.println(appleBox1.compareTo(orangeBox1));
    }
}

abstract class Fruit {
}

class Apple extends Fruit {
}

class Orange extends Fruit {
}
