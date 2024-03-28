package ru.progwards.java2.lessons.patterns.AsbIntegerFactory;

public enum AbstractFactory {
    INSTANCE;

    public AbsNumber getAbsInteger(String factoryID, int number) {
        AbstractNumberFactory numberFactory = null;
        switch (factoryID) {
            case "AbsIntegerFactory":
                numberFactory = AbsIntegerFactory.INSTANCE;
        }

        return numberFactory == null ? null : numberFactory.getAbsInteger(number);
    }
}
