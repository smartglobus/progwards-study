package ru.progwards.java2.lessons.annotation;

import ru.progwards.java2.lessons.calculator.Calculator;
import ru.progwards.java2.lessons.tests.calc.SimpleCalculator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JTest {

    SimCalcJTest test = new SimCalcJTest();

    void run(String name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class cls = Class.forName(name);
        Constructor constructor = cls.getConstructor();
        Object calcTst = constructor.newInstance();
        Method init = cls.getDeclaredMethod("initCalc");
        init.invoke(calcTst);
        Method method = cls.getDeclaredMethod("sumTest");
        method.setAccessible(true);
        method.invoke(calcTst);
//        Annotation annotation = method.getAnnotation(Test.class);
//        System.out.println(method.getAnnotation(Test.class).priority().getPriority());
    }


    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        JTest tst = new JTest();
//        tst.initCalc();
//        System.out.println(tst.sumTest());
        tst.run("ru.progwards.java2.lessons.annotation.SimCalcJTest");
    }
}
