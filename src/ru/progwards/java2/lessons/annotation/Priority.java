package ru.progwards.java2.lessons.annotation;

public enum Priority {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    private final int priority;

    Priority(int value) {
        this.priority = value;
    }

    public int getPriority() {
        return priority;
    }
}
