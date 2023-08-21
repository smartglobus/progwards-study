package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

import ru.progwards.java2.lessons.tests.calc.SimpleCalculator;

import java.util.Arrays;

@RunWith(Enclosed.class)
public class SimpleCalculatorTest {

    @RunWith(Parameterized.class)
    public static class SumTest {
        private static SimpleCalculator calc = new SimpleCalculator();
        int val1;
        int val2;
        int result;

        public SumTest(int val1, int val2, int result) {
            this.val1 = val1;
            this.val2 = val2;
            this.result = result;
        }

        @Parameterized.Parameters(name = "Тест {index}:  {0}+{1}={2}")
        public static Iterable<Object[]> dataSet() {
            return Arrays.asList(new Object[][]{
                    {1, -1, 0},
                    {Integer.MAX_VALUE, 0, Integer.MAX_VALUE},
                    {Integer.MAX_VALUE, Integer.MIN_VALUE, -1}
            });
        }

        @Test
        public void testSum() {
            assertEquals(result, calc.sum(val1, val2));
        }

        @AfterClass
        public static void destroy() {
            calc = null;
        }
    }


    @RunWith(Parameterized.class)
    public static class DiffTest {
        private static SimpleCalculator calc = new SimpleCalculator();
        int val1;
        int val2;
        int result;

        public DiffTest(int val1, int val2, int result) {
            this.val1 = val1;
            this.val2 = val2;
            this.result = result;
        }

        @Parameterized.Parameters(name = "Тест {index}:  {0}-{1}={2}")
        public static Iterable<Object[]> dataSet() {
            return Arrays.asList(new Object[][]{
                    {0, 0, 0},
                    {5, 2, 3},
                    {Integer.MIN_VALUE + 1, 1, Integer.MIN_VALUE}
            });
        }

        @Test
        public void testDiff() {
            assertEquals(result, calc.diff(val1, val2));
        }

        @AfterClass
        public static void destroy() {
            calc = null;
        }
    }


    @RunWith(Parameterized.class)
    public static class MultTest {
        private static SimpleCalculator calc = new SimpleCalculator();
        int val1;
        int val2;
        int result;

        public MultTest(int val1, int val2, int result) {
            this.val1 = val1;
            this.val2 = val2;
            this.result = result;
        }

        @Parameterized.Parameters
        public static Iterable<Object[]> dataSet() {
            return Arrays.asList(new Object[][]{
                    {10, 0, 0},
                    {7, 7, 49}
            });
        }

        @Test
        public void testMult() {
            assertEquals(result, calc.mult(val1, val2));
        }

        @AfterClass
        public static void destroy() {
            calc = null;
        }
    }


    @RunWith(Parameterized.class)
    public static class DivTest {
        private static SimpleCalculator calc = new SimpleCalculator();
        int val1;
        int val2;
        int result;

        public DivTest(int val1, int val2, int result) {
            this.val1 = val1;
            this.val2 = val2;
            this.result = result;
        }

        @Parameterized.Parameters
        public static Iterable<Object[]> dataSet() {
            return Arrays.asList(new Object[][]{
                    {7, 2, 3},
                    {7, 7, 1}
            });
        }

        @Test
        public void testDiv() {
            assertEquals(result, calc.div(val1, val2));
        }

        @AfterClass
        public static void destroy() {
            calc = null;
        }
    }
}
