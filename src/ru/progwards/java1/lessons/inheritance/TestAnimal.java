package ru.progwards.java1.lessons.inheritance;

public class TestAnimal {

    public static void printAnimal(Animal animal) {
        System.out.println(animal.toString() + " - " + animal.say() + "!");
    }

    public static void main(String[] args) {
        Cow cowPestrushka = new Cow("Пеструшка");
        printAnimal(cowPestrushka);
        Cow cowRyzhaya = new Cow("Рыжая");
        printAnimal(cowRyzhaya);
        Duck duckDasha = new Duck("Даша");
        printAnimal(duckDasha);
        Duck duckMasha = new Duck("Маша");
        printAnimal(duckMasha);
        Hamster hamsterAkakiy = new Hamster("Акакий");
        printAnimal(hamsterAkakiy);
        Hamster hamsterPolykapr = new Hamster("Поликарп");
        printAnimal(hamsterPolykapr);
    }

}
