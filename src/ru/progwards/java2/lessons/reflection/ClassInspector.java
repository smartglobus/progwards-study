package ru.progwards.java2.lessons.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassInspector {
    public static void inspect(String clazz) throws ClassNotFoundException {

        Class cls = Class.forName(clazz);
        System.out.print(Modifier.toString(cls.getModifiers()) + " class ");

        String[] clsName = cls.getName().split("\\.");

        System.out.print(clsName[clsName.length - 1] + "{\n");
        Field[] fields = cls.getDeclaredFields();
        Constructor[] constructors = cls.getDeclaredConstructors();
        Method[] methods = cls.getDeclaredMethods();

        for (Field f : fields) {
            System.out.print("\t" + Modifier.toString(f.getModifiers()) + " ");
            System.out.println(f.getName() + ";");
        }
        for (Constructor c : constructors) {
            System.out.print("\t" + Modifier.toString(c.getModifiers()) + " ");
            String[] name = c.getName().split("\\.");
            System.out.print(name[name.length - 1] + "(");
            Class[] classes = c.getParameterTypes();
            if (classes.length == 0) System.out.println(") {}");
            for (int i = 0; i < classes.length; i++) {
                Class parCls = classes[i];
                System.out.print(parCls.getSimpleName());
                System.out.print(i == classes.length - 1 ? " arg" + i + ") {}\n" : " arg" + i + ", ");
            }
        }
        for (Method m : methods) {
            System.out.print("\t" + Modifier.toString(m.getModifiers()) + " ");
            System.out.print(m.getName() + "(");
            Class[] classes = m.getParameterTypes();
            if (classes.length == 0) System.out.println(") {}");
            for (int i = 0; i < classes.length; i++) {
                Class parCls = classes[i];
                System.out.print(parCls.getSimpleName());
                System.out.print(i == classes.length - 1 ? " arg" + i + ") {}\n" : " arg" + i + ", ");
            }
        }
        System.out.println("}");
    }

    public static void main(String[] args) throws ClassNotFoundException {

        inspect("ru.progwards.java2.lessons.reflection.Employee");
    }
}
