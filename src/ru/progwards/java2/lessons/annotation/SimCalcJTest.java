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
        testSet.add(new TestDataUnit(Operation.DIFF, Integer.MIN_VALUE, 25, 0));
        testSet.add(new TestDataUnit(Operation.DIFF, 35, 20, 20));
        testSet.add(new TestDataUnit(Operation.DIFF, 15, 7, 8));

        testSet.add(new TestDataUnit(Operation.SUM, 5, 7, 12));
        testSet.add(new TestDataUnit(Operation.SUM, Integer.MAX_VALUE, 25, 0));
        testSet.add(new TestDataUnit(Operation.SUM, 35, 20, 20));
        testSet.add(new TestDataUnit(Operation.SUM, 15, 7, 22));

        testSet.add(new TestDataUnit(Operation.MULT, 5, 7, 35));
        testSet.add(new TestDataUnit(Operation.MULT, Integer.MAX_VALUE, 25, 0));
        testSet.add(new TestDataUnit(Operation.MULT, 35, 20, 20));
        testSet.add(new TestDataUnit(Operation.MULT, 15, 7, 105));

        testSet.add(new TestDataUnit(Operation.DIV, 5, 7, 0));
        testSet.add(new TestDataUnit(Operation.DIV, Integer.MAX_VALUE, 0, 0));
        testSet.add(new TestDataUnit(Operation.DIV, 35, 20, 20));
        testSet.add(new TestDataUnit(Operation.DIV, 15, 7, 2));
    }


    @Test(priority = Priority.ONE)
    private boolean sumTest() {
        Map<TestDataUnit, Boolean> testResMap = new HashMap<>();
        for (TestDataUnit tdu : testSet) {
            if (tdu.operation == Operation.SUM) {
                try {
                    boolean res = simCalc.sum(tdu.a, tdu.b) == tdu.result;
                    testResMap.put(tdu, res);
                } catch (ArithmeticException e) {
                    testResMap.put(tdu, false);
                    System.err.printf("При вычислении суммы %d и %d произошло исключение. ", tdu.a, tdu.b);
                    System.err.println(e.getMessage());
                }
            }
        }
        boolean outRes = true;
        for (TestDataUnit tdu : testResMap.keySet()) {
            boolean testRes = testResMap.get(tdu);
            if (!testRes)
                System.out.printf("Неверный результат при вычислении суммы %d и %d.\n", tdu.a, tdu.b);
            outRes &= testRes;
        }
        return outRes;
    }

    @Test(priority = Priority.TWO)
    private boolean diffTest() {
        Map<TestDataUnit, Boolean> testResMap = new HashMap<>();
        for (TestDataUnit tdu : testSet) {
            if (tdu.operation == Operation.DIFF) {
                try {
                    boolean res = simCalc.diff(tdu.a, tdu.b) == tdu.result;
                    testResMap.put(tdu, res);
                } catch (ArithmeticException e) {
                    testResMap.put(tdu, false);
                    System.err.printf("При вычислении разницы %d и %d произошло исключение. ", tdu.a, tdu.b);
                    System.err.println(e.getMessage());
                }
            }
        }
        boolean outRes = true;
        for (TestDataUnit tdu : testResMap.keySet()) {
            boolean testRes = testResMap.get(tdu);
            if (!testRes)
                System.out.printf("Неверный результат при вычислении разницы %d и %d.\n", tdu.a, tdu.b);
            outRes &= testRes;
        }
        return outRes;
    }

    @Test(priority = Priority.THREE)
    private boolean multTest() {
        Map<TestDataUnit, Boolean> testResMap = new HashMap<>();
        for (TestDataUnit tdu : testSet) {
            if (tdu.operation == Operation.MULT) {
                try {
                    boolean res = simCalc.mult(tdu.a, tdu.b) == tdu.result;
                    testResMap.put(tdu, res);
                } catch (ArithmeticException e) {
                    testResMap.put(tdu, false);
                    System.err.printf("При вычислении произведения %d и %d произошло исключение. ", tdu.a, tdu.b);
                    System.err.println(e.getMessage());
                }
            }
        }
        boolean outRes = true;
        for (TestDataUnit tdu : testResMap.keySet()) {
            boolean testRes = testResMap.get(tdu);
            if (!testRes)
                System.out.printf("Неверный результат при вычислении произведения %d и %d.\n", tdu.a, tdu.b);
            outRes &= testRes;
        }
        return outRes;
    }

    @Test(priority = Priority.ONE)
    private boolean divTest() {
        Map<TestDataUnit, Boolean> testResMap = new HashMap<>();
        for (TestDataUnit tdu : testSet) {
            if (tdu.operation == Operation.DIV) {
                try {
                    boolean res = simCalc.div(tdu.a, tdu.b) == tdu.result;
                    testResMap.put(tdu, res);
                } catch (ArithmeticException e) {
                    testResMap.put(tdu, false);
                    System.err.printf("При выполнении деления %d на %d произошло исключение. ", tdu.a, tdu.b);
                    System.err.println(e.getMessage());
                }
            }
        }
        boolean outRes = true;
        for (TestDataUnit tdu : testResMap.keySet()) {
            boolean testRes = testResMap.get(tdu);
            if (!testRes)
                System.out.printf("Неверный результат при делении %d на %d.\n", tdu.a, tdu.b);
            outRes &= testRes;
        }
        return outRes;
    }


    @After
    void killCalk() {
        simCalc = null;
    }


    public static void main(String[] args) {
        SimCalcJTest simCalcJTest = new SimCalcJTest();
        simCalcJTest.initCalc();
        System.out.println("Diff test result = " + simCalcJTest.diffTest());
        System.out.println("Sum test result = " + simCalcJTest.sumTest());
        System.out.println("Mult test result = " + simCalcJTest.multTest());
        System.out.println("Div test result = " + simCalcJTest.divTest());
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