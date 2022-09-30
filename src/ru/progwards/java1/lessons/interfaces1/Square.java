package ru.progwards.java1.lessons.interfaces1;

public class Square implements Area {
    double segment;

    Square(double segment) {
        this.segment = segment;
    }

    @Override
    public double getArea() {
        return segment * segment;
    }
}




