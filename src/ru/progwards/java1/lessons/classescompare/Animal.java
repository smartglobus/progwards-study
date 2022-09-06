package ru.progwards.java1.lessons.classescompare;

public abstract class Animal {
    String name;
    double weight;

    public Animal() {
    }

    public Animal(String name) {
        this.name = name;
    }

    public abstract String kind();

    public abstract String say();

    public String toString() {
        return "Это " + kind() + " " + name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    int compareTo(Animal animal) {
//        if (getWeight()>animal.getWeight()){return 1;}
//        if (getWeight()<animal.getWeight()){return -1;}
//        return 0;
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

class Cow extends Animal {
    public Cow(String name) {
        super(name);
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
}

class Hamster extends Animal {
    public Hamster(String name) {
        super(name);
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
}

class Duck extends Animal {
    public Duck(String name) {
        super(name);
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

}

