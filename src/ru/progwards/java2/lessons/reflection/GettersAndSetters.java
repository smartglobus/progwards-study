package ru.progwards.java2.lessons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GettersAndSetters {
    public static void check(String clazz) throws ClassNotFoundException {
        Class cls = Class.forName(clazz);
        Field[] fields = cls.getDeclaredFields();
        List<String> methodsNames = new ArrayList<>();
        Method[] methods = cls.getDeclaredMethods();
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
                    try {
                        Method method = cls.getDeclaredMethod(setter, f.getType());
                        String methodModifiers = Modifier.toString(method.getModifiers());
                        if (!methodModifiers.contains("public") || methodModifiers.contains("static"))
                            System.out.println("public void " + setter + "(" + fTypeName + " " + fName + ")");
                    } catch (NoSuchMethodException e) {
                        System.out.println("public void " + setter + "(" + fTypeName + " " + fName + ")");
                    }
                }
                if (!methodsNames.contains(getter))
                    System.out.println("public " + fTypeName + " " + getter + "()");
                else {
                    try {
                        Method method = cls.getDeclaredMethod(getter);
                        String methodModifiers = Modifier.toString(method.getModifiers());
                        if (!methodModifiers.contains("public") || methodModifiers.contains("static"))
                            System.out.println("public " + fTypeName + " " + getter + "()");
                    } catch (NoSuchMethodException e) {
                        System.out.println("public " + fTypeName + " " + getter + "()");
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            check("ru.progwards.java2.lessons.reflection.Employee");
            System.out.println("--------------");
            check("ru.progwards.java2.lessons.gc.Heap");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
