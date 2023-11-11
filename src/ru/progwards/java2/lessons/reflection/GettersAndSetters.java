package ru.progwards.java2.lessons.reflection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GettersAndSetters {
    public static void check(String clazz) throws ClassNotFoundException {
        Class cls = Class.forName(clazz);
        Field[] fields = cls.getDeclaredFields();
        Method[] methods = cls.getDeclaredMethods();
        List<String> methodsNames = new ArrayList<>();
        Arrays.stream(methods).forEach(method -> methodsNames.add(method.getName()));

        for (Field f : fields) {
            String modifiers = Modifier.toString(f.getModifiers());
            if (modifiers.contains("private")) {
                String fName = f.getName();
                String fTypeName = f.getType().getSimpleName();
                String fNameFirstCapitalLetter = fName.substring(0, 1).toUpperCase() + fName.substring(1);
                String setter = "set" + fNameFirstCapitalLetter;
                String getter = "get" + fNameFirstCapitalLetter;
                if (!methodsNames.contains(setter))
                    System.out.println("public void " + setter + "(" + fTypeName + " " + fName + ")");
                else {
                    for (Method m : methods) {
                        if (m.getName().equals(setter)) {
                            Parameter[] parameters = m.getParameters();
                            String methodModifiers = Modifier.toString(m.getModifiers());
                            if (parameters.length != 1 || !methodModifiers.contains("public") || methodModifiers.contains("static"))
                                System.out.println("public void " + setter + "(" + fTypeName + " " + fName + ")");
                        }
                    }
                }
                if (!methodsNames.contains(getter))
                    System.out.println("public " + fTypeName + " " + getter + "()");
                else {
                    for (Method m : methods) {
                        if (m.getName().equals(getter)) {
                            Parameter[] parameters = m.getParameters();
                            String methodModifiers = Modifier.toString(m.getModifiers());
                            if (parameters.length > 0 || !methodModifiers.contains("public") || methodModifiers.contains("static"))
                                System.out.println("public " + fTypeName + " " + getter + "()");
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            check("ru.progwards.java2.lessons.reflection.Employee");
            System.out.println("--------------");
            check("ru.progwards.java2.lessons.gc.Heap");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
