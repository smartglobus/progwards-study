package ru.progwards.java2.lessons.patterns.AsbIntegerFactory;

public class AbstractFactoryClient {
    public static void main(String[] args) {
        AbsNumber absNumber = AbstractFactory.INSTANCE.getAbsInteger("AbsIntegerFactory", 55);
        if (absNumber == null) {
            System.out.println("wrong parameters");
        } else {
            if (absNumber instanceof ByteInteger) {
                System.out.println("ByteInteger " + absNumber);
            }
            if (absNumber instanceof ShortInteger) {
                System.out.println("ShortInteger " + absNumber);
            }
            if (absNumber instanceof IntInteger) {
                System.out.println("IntInteger " + absNumber);
            }
        }
    }
}
