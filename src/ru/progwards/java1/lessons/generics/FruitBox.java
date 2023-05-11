package ru.progwards.java1.lessons.generics;

import java.util.ArrayList;

public class FruitBox<E extends Fruit> extends ArrayList<Fruit> implements Comparable<FruitBox> {

    @Override
    public boolean add(Fruit f) {
        if (size() == 0 || get(0).getClass().equals(f.getClass()))
            return super.add(f);
        return false;
    }

    public float getWeight() {
        return size() > 0 ? size() * get(0).getWeight() : 0;
    }

    void moveTo(FruitBox<Fruit> fb) throws UnsupportedOperationException {
        try {
            if (get(0).getClass().equals(fb.get(0).getClass())) {
                fb.addAll(this);
                clear();
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public int compareTo(FruitBox fb) {
        return Float.compare(getWeight(), fb.getWeight());
    }


    public static void main(String[] args) {
        FruitBox appleBox1 = new FruitBox<>();
        FruitBox appleBox2 = new FruitBox<>();
        FruitBox orangeBox1 = new FruitBox<>();

        System.out.println(appleBox1.add(new Apple()));
        System.out.println(appleBox1.add(new Orange()));
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
    abstract float getWeight();
}

class Apple extends Fruit {
    @Override
    public float getWeight() {
        return 1f;
    }
}

class Orange extends Fruit {
    @Override
    public float getWeight() {
        return 1.5f;
    }
}
