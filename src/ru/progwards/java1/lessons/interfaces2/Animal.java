package ru.progwards.java1.lessons.interfaces2;

import java.util.Arrays;
import java.util.Objects;

public abstract class Animal implements IColor, Comparable<Animal>, Home, ToString, CompareWeight {
    String name;
    double weight;

    public Animal() {
    }

    public Animal(String name) {
        this.name = name;
    }

    public Animal(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public abstract String kind();

    public abstract String say();

    public String toString() {
        return "Это " + kind() + " " + name + " " + weight + " " + getColor();
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Animal animal) {
        return Double.compare(getWeight(), animal.getWeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Animal)) {
            return false;
        }
        Animal animal = (Animal) o;
        if (name.equals(animal.name) && getWeight() == animal.getWeight() && kind().equals(animal.kind())) {
            return true;
        }
        return false;
    }

    enum FoodKind {
        HAY,
        CORN,
    }

    abstract public FoodKind getFoodKind();

    abstract public double getFoodCoeff();

    public double calculateFoodWeight() {
        return getWeight() * getFoodCoeff();
    }

    public double calculateFoodPrice() {
        if (getFoodKind().equals(FoodKind.HAY)) return calculateFoodWeight() * 2;
        if (getFoodKind().equals(FoodKind.CORN)) return calculateFoodWeight() * 15;
        return 0;
    }

    @Override
    public String getString() {
        return toString();
    }

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeight) {
        if (getWeight() < smthHasWeight.getWeight()) {
            return CompareResult.LESS;
        }
        if (getWeight() == smthHasWeight.getWeight()) {
            return CompareResult.EQUAL;
        }
        return CompareResult.GREATER;
    }
}

class TestCompare {

    public static void main(String[] args) {

        Animal pestruha = new Cow("Пеструшка", 350.0);
        Animal ryzhaya = new Cow("Рыжая", 250.0);
        Animal dasha = new Duck("Даша", 3.0);
        Animal masha = new Duck("Маша", 3.5);
        Animal akakiy = new Hamster("Акакий", 0.25);
        Animal polykarp = new Hamster("Поликарп", 0.32);

        Animal[] animals = {pestruha, ryzhaya, dasha, masha, akakiy, polykarp};
        Arrays.sort(animals);
        System.out.println(Arrays.toString(animals));
    }
}


class Cow extends Animal implements IColor {
    public Cow(String name) {
        super(name);
    }

    public Cow(String name, double weight) {
        super(name, weight);
    }

    @Override
    public String kind() {
        return "корова";
    }

    public String say() {
        return "мууууууу";
    }

    @Override
    public FoodKind getFoodKind() {
        return FoodKind.HAY;
    }

    @Override
    public double getFoodCoeff() {
        return 0.05;
    }

    @Override
    public String getHome() {
        return "ферма";
    }

    @Override
    public Color getColor() {
        return Color.RED;

    }
}

class Hamster extends Animal implements IColor {
    public Hamster(String name) {
        super(name);
    }

    public Hamster(String name, double weight) {
        super(name, weight);
    }

    @Override
    public String kind() {
        return "хомяк";
    }

    public String say() {
        return "хрум-хрум-хрум";
    }

    @Override
    public FoodKind getFoodKind() {
        return FoodKind.CORN;
    }

    @Override
    public double getFoodCoeff() {
        return 0.06;
    }

    @Override
    public String getHome() {
        return "поле";
    }

    @Override
    public Color getColor() {
        return Color.BEIGE;
    }
}

class Duck extends Animal implements IColor {
    public Duck(String name) {
        super(name);
    }

    public Duck(String name, double weight) {
        super(name, weight);
    }

    @Override
    public String kind() {
        return "утка";
    }

    public String say() {
        return "кря-кря";
    }

    @Override
    public FoodKind getFoodKind() {
        return FoodKind.CORN;
    }

    @Override
    public double getFoodCoeff() {
        return 0.04;
    }

    @Override
    public String getHome() {
        return "ферма";
    }

    @Override
    public Color getColor() {
        return Color.GRAY;
    }
}


class Flower implements IColor {

    @Override
    public Color getColor() {
        return Color.WHITE;
    }
}


class Hawk implements Home {

    @Override
    public String getHome() {
        return "поле";
    }
}

class CompareHome {

    public static boolean sameHome(Home h1, Home h2) {
        boolean res = h1.getHome().equals(h2.getHome());
        String xxx1 = h1.getClass().getSimpleName();
        String xxx2 = h2.getClass().getSimpleName();
        System.out.println("Результат сравнения " + xxx1 + " и " + xxx2 + " равен " + res);

        return res;
    }

    public static void main(String[] args) {
        Home cow = new Cow("mu");
        Home duck = new Duck("krya");
        Home hamster = new Hamster("hrum");
        Home hawk = new Hawk();
        sameHome(cow, duck);
        sameHome(cow, hamster);
        sameHome(cow, hawk);
        sameHome(duck, hamster);
        sameHome(duck, hawk);
        sameHome(hamster, hawk);
    }
}

class TestString {

    public static void print(ToString any) {
        System.out.println(any.getString());
    }

    public static void main(String[] args) {
        Animal pestruha = new Cow("Пеструшка");
        print(pestruha);
        Animal dasha = new Duck("Даша");
        print(dasha);
        Animal polykarp = new Hamster("Поликарп");
        print(polykarp);
        Time time = new Time(1, 12, 55);
        print(time);
        ComplexNum complexNum = new ComplexNum(22, 5);
        print(complexNum);


    }
}

class Car implements CompareWeight {
    double weight;

    Car(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeight) {
        if (getWeight() < smthHasWeight.getWeight()) {
            return CompareResult.LESS;
        }
        if (getWeight() == smthHasWeight.getWeight()) {
            return CompareResult.EQUAL;
        }
        return CompareResult.GREATER;
    }

    public String toString() {
        return "Это автомобиль грузоподъемностью " + weight;
    }


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

    public static void main(String[] args) {
        Car ford = new Car(1450);
        Car honda = new Car(350);
        Animal pestruha = new Cow("Пеструшка", 350.0);
        Animal dasha = new Duck("Даша", 3.0);
        Animal polykarp = new Hamster("Поликарп", 0.32);
        Animal ryzhaya = new Cow("Рыжая", 250.0);
        Animal masha = new Duck("Маша", 3.5);
        Animal akakiy = new Hamster("Акакий", 0.25);

        CompareWeight[] wht = {ford,honda,pestruha,dasha,polykarp,ryzhaya,masha,akakiy};
        sort(wht);
        System.out.println(Arrays.toString(wht));
        System.out.println(ford.compareWeight(honda));
        System.out.println(ryzhaya.compareWeight(pestruha));
        System.out.println(pestruha.compareWeight(honda));
    }
}