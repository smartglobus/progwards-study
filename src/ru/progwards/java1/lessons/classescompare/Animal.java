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

    public static void main(String[] args) {
        System.out.println("Тест для Animal, Cow, Hamster, Duck");
        Animal muha = new Cow("Муха");
        Animal maha = new Cow("Маха");
        System.out.println(muha.equals(maha));//diff. names
        maha.name = "Муха";
        muha.setWeight(251.6);
        System.out.println(muha.equals(maha));//diff. weight
        maha.setWeight(muha.getWeight());
        System.out.println(muha.equals(maha));//all equals
        Animal gaga = new Duck("Муха");
        gaga.setWeight(maha.getWeight());
        System.out.println(gaga.equals(maha));//diff. kind
        System.out.print(muha.name + " weights " + muha.getWeight() + "kg and");
        System.out.println(" eats " + muha.getFoodKind() + ", food coeff =" + Double.toString(muha.getFoodCoeff()));
        System.out.println(muha.name + " eats " + muha.calculateFoodWeight() + "kg per day");
        System.out.println("this costs her owner " + muha.calculateFoodPrice() + " bucks per day");

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



