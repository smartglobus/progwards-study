package ru.progwards.java1.lessons.interfaces2;

public interface CompareWeight {
    double getWeight();

    CompareResult compareWeight(CompareWeight smthHasWeight);

    public enum CompareResult {
        LESS,
        EQUAL,
        GREATER
    }
}
