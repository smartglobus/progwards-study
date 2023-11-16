package ru.progwards.java2.lessons.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class JTest {
    private Object classForJTest;

    class MetodWithPriority implements Comparable<MetodWithPriority> {
        int priority;
        Method method;

        MetodWithPriority(int priority, Method method) {
            this.priority = priority;
            this.method = method;
        }

        @Override
        public int compareTo(MetodWithPriority o) {
            return Integer.compare(priority, o.priority);
        }
    }

    List<MetodWithPriority> priorityList = new ArrayList<>();

    void run(String name) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, RuntimeException, NoSuchMethodException, InstantiationException {
        Class cls = Class.forName(name);
        Constructor constructor = cls.getConstructor();
        classForJTest = constructor.newInstance();

        Method[] methods = cls.getDeclaredMethods();
        int priority;
        boolean isBeforePresent = false, isAfterPresent = false;
        for (Method m : methods) {
            m.setAccessible(true);
            if (m.isAnnotationPresent(Before.class)) {
                if (!isBeforePresent) {
                    priorityList.add(new MetodWithPriority(0, m));
                    isBeforePresent = true;
                } else
                    throw new RuntimeException("В тестовом классе может быть только один метод с аннотацией Before.");
            }
            if (m.isAnnotationPresent(After.class)) {
                if (!isAfterPresent) {
                    priorityList.add(new MetodWithPriority(11, m));
                    isAfterPresent = true;
                } else
                    throw new RuntimeException("В тестовом классе может быть только один метод с аннотацией After.");
            }
            if (m.isAnnotationPresent(Test.class)) {
                priority = m.getAnnotation(Test.class).priority().getPriority();
                priorityList.add(new MetodWithPriority(priority, m));
            }
        }

        boolean result = true;
        priorityList.sort(MetodWithPriority::compareTo);
        for (MetodWithPriority mwp : priorityList) {
            Method m = mwp.method;
            if (m.getReturnType() == boolean.class)
                result &= (boolean) m.invoke(classForJTest);
            else m.invoke(classForJTest);
        }
        System.out.println(result ? "Все тесты пройдены успешно!" : "\nВ ходе тестов обнаружены ошибки!");
    }


    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        JTest tst = new JTest();
        tst.run("ru.progwards.java2.lessons.annotation.SimCalcJTest");
        System.out.println();
    }
}
