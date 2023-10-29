package ru.progwards.java2.lessons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GettersAndSetters {
    public static void check(String clazz) throws ClassNotFoundException {
        Class cls = Class.forName(clazz);
        Field[] fields = cls.getDeclaredFields();
        List<String> methodsNames = new ArrayList<>();
        Arrays.stream(cls.getDeclaredMethods()).forEach(method -> methodsNames.add(method.getName()));

        for (Field f : fields) {
            String modifiers = Modifier.toString(f.getModifiers());
            if (modifiers.contains("private")) {
                String fName = f.getName();
                String fNameFirstCapitalLetter = fName.substring(0, 1) + fName.substring(1);
                if (!methodsNames.contains("set" + fNameFirstCapitalLetter)) {

                }
                if (!methodsNames.contains("get" + fNameFirstCapitalLetter)) {

                }
            }

        }
//        System.out.println(Arrays.toString(fields));

    }

    public static void main(String[] args) {
        try {
            check("ru.progwards.java2.lessons.reflection.Employee");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
