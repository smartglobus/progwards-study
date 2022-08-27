package ru.progwards.java1.lessons.inheritance;

public abstract class Animal {
    String name;

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
}

class Cow extends Animal {
    public Cow(String name) {
        super(name);
    }

    @Override
    public String kind() { return "корова"; }
    public String say() {
        return "мууууууу";
    }
}

class Hamster extends Animal {
    public Hamster(String name){
        super(name);
    }

    @Override
    public String kind() {return "хомяк";}
    public String say() {
        return "хрум-хрум-хрум";
    }
}

class Duck extends Animal {
    public Duck(String name) {
        super(name);
    }

    @Override
    public String kind() { return "утка"; }
    public String say() {
        return "кря-кря";
    }

}

