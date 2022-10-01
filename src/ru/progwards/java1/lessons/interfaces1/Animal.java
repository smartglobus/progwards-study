package ru.progwards.java1.lessons.interfaces1;


import java.util.Arrays;

public abstract class Animal implements IColor, Comparable<Animal> {
    String name;
    double weight;

    public Animal() {
    }

    public Animal(String name) {
        this.name = name;
    }

    public Animal(String name, double weight){
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


    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Animal animal) {
        return Double.compare(getWeight(), animal.getWeight());
    }

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


}

class TestCompare {


    public static void main(String[] args) {


        Animal pestruha = new Cow("Пеструшка", 350.0);
        Animal ryzhaya = new Cow("Рыжая",250.0);
        Animal dasha = new Duck("Даша", 3.0);
        Animal masha = new Duck("Маша", 3.5);
        Animal akakiy = new Hamster("Акакий", 0.25);
        Animal polykarp = new Hamster("Поликарп", 0.32);

        Animal[] animals = {pestruha, ryzhaya, dasha, masha, akakiy, polykarp};
        Arrays.sort(animals);
        System.out.println(Arrays.toString(animals));

    }
}

class Cow extends Animal implements IColor{
    public Cow(String name) {
        super(name);
    }
    public Cow (String name, double weight){
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
    public Color getColor(){
        return Color.RED;

    }
}

class Hamster extends Animal implements IColor {
    public Hamster(String name) {
        super(name);
    }
    public Hamster(String name, double weight){
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
    public Color getColor(){
        return Color.BEIGE;
    }
}

class Duck extends Animal implements IColor{
    public Duck(String name) {
        super(name);
    }
    public Duck (String name, double weight){
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
    public Color getColor(){
        return Color.GRAY;
    }
}

class Flower implements IColor {

    @Override
    public Color getColor(){
        return Color.WHITE;
    }
}



