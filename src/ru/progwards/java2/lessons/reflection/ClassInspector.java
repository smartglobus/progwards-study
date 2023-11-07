package ru.progwards.java2.lessons.reflection;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassInspector {
    public static void inspect(String clazz) throws ClassNotFoundException {
        Class cls = Class.forName(clazz);
        String clsSimpleName = cls.getSimpleName();
        try (FileWriter writer = new FileWriter(clsSimpleName + ".java")) {
            writer.write(Modifier.toString(cls.getModifiers()) + " class ");
            writer.write(clsSimpleName + " {");
            Field[] fields = cls.getDeclaredFields();
            Constructor[] constructors = cls.getDeclaredConstructors();
            Method[] methods = cls.getDeclaredMethods();

            for (Field f : fields) {
                writer.write("\n\t" + Modifier.toString(f.getModifiers()) + " ");
                writer.write(f.getType().getSimpleName() + " ");
                writer.write(f.getName() + ";");
            }
            for (Constructor c : constructors) {
                writer.write("\n\t" + Modifier.toString(c.getModifiers()) + " ");
                String[] name = c.getName().split("\\.");
                writer.write(name[name.length - 1] + "(");
                Class[] classes = c.getParameterTypes();
                if (classes.length == 0) writer.write(") {}");
                for (int i = 0; i < classes.length; i++) {
                    Class parCls = classes[i];
                    writer.write(parCls.getSimpleName());
                    writer.write(i == classes.length - 1 ? " arg" + i + ") {}" : " arg" + i + ", ");
                }
            }
            for (Method m : methods) {
                writer.write("\n\t" + Modifier.toString(m.getModifiers()) + " ");
                writer.write(m.getReturnType().getSimpleName() + " ");
                writer.write(m.getName() + "(");
                Class[] classes = m.getParameterTypes();
                if (classes.length == 0) writer.write(") {}");
                for (int i = 0; i < classes.length; i++) {
                    Class parCls = classes[i];
                    writer.write(parCls.getSimpleName());
                    writer.write(i == classes.length - 1 ? " arg" + i + ") {}" : " arg" + i + ", ");
                }
            }
            writer.write("\n}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        inspect("ru.progwards.java2.lessons.reflection.Employee");
    }
}
