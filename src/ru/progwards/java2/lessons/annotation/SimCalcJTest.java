package ru.progwards.java2.lessons.annotation;

import ru.progwards.java2.lessons.tests.calc.SimpleCalculator;

import java.util.*;

public class SimCalcJTest {
    SimpleCalculator simCalc;
    Set<TestDataUnit> testSet = new HashSet<>();

    @Before
    void initCalc() {
        simCalc = new SimpleCalculator();
        testSet.add(new TestDataUnit(Operation.DIFF, 5, 7, -2));

    }


    @Test(priority = Priority.ONE)
    private boolean sumTest() {
        List<Boolean> results = new ArrayList<>();
        boolean res = true;
        results.add(simCalc.sum(-6, 8) == 2);
        results.add(simCalc.sum(0, 88) == 88);
        results.add(simCalc.sum(6, 8) == 14);
//        boolean a = simCalc.sum(-6, 8) == 2;
//        boolean b = simCalc.sum(0, 88) == 88;
//        boolean c = simCalc.sum(6, 8) == 14;
        for (boolean bb : results) {
            res &= bb;
        }
        System.out.println(res);
        return res;
    }

    @Test(priority = Priority.TWO)
    private void diffTest() {
        Map<TestDataUnit, Boolean> testResMap = new HashMap<>();
        for (TestDataUnit tdu : testSet) {
            if (tdu.operation == Operation.DIFF) {
                try {
                    boolean res = simCalc.diff(tdu.a, tdu.b) == tdu.result;
                    testResMap.put(tdu,res);
//                    return res;
                } catch (ArithmeticException e) {
                    testResMap.put(tdu, false);
                    System.out.printf("При выполнении проверки вычисления разницы между %d и %d произошло исключение. ", tdu.a, tdu.b);
                    System.out.println(e.getMessage());
                }
            }
        }

//        return false;
    }

    @Test(priority = Priority.THREE)
    private boolean multTest() {
        return false;
    }

    @Test(priority = Priority.FOUR)
    private boolean divTest() {
        return false;
    }


    @After
    void killCalk() {
        simCalc = null;
    }
}

enum Operation {
    SUM, DIFF, MULT, DIV
}

class TestDataUnit {
    Operation operation;
    int a, b;
    int result;

    public TestDataUnit(Operation operation, int a, int b, int result) {
        this.operation = operation;
        this.a = a;
        this.b = b;
        this.result = result;
    }
}