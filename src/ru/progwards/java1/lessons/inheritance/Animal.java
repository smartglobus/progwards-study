package ru.progwards.java1.lessons.inheritance;

/**
 * Класс Animal
 * Родитель всех зверушек
 * @author God
 * @version 1.0
 * @see Cow
 *
 */
public abstract class Animal {
    String name;

    /**
     * Конструктор по умолчанию.
     */
    public Animal() {
    }

    /**
     * Конструктор с именем
     * @param name как звать звершку
     */
    public Animal(String name) {
        this.name = name;
    }

    public abstract String kind();

    /**
     *
     * @return Возвращает основной оборот речи животного, используемый для внутривидового обмена информацией
     */
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

