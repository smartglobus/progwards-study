package ru.progwards.java2.lessons.tests.test.calc;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.progwards.java2.lessons.tests.calc.SimpleCalculator;

import static org.junit.Assert.*;


public class SimpleCalculatorExceptionsTest {
    private static SimpleCalculator calc;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void init() {
        calc = new SimpleCalculator();
    }

    @Test
    public void sumTest() {
        expectedException.expect(ArithmeticException.class);
        expectedException.expectMessage("Переполнение диапазона int в методе sum.");
        assertEquals((long) Integer.MAX_VALUE + 1, calc.sum(Integer.MAX_VALUE, 1));
    }

    @Test
    public void diffTest() {
        expectedException.expect(ArithmeticException.class);
        expectedException.expectMessage("Переполнение диапазона int в методе sum, вызванного из метода diff");
        assertEquals(Integer.MAX_VALUE, calc.diff(Integer.MIN_VALUE, 1));
    }

    @Test
    public void mulTest() {
        expectedException.expect(ArithmeticException.class);
        expectedException.expectMessage("Переполнение диапазона int в методе mult.");
        assertEquals((long) Integer.MIN_VALUE * 2, calc.mult(Integer.MIN_VALUE, 2));
    }

    @Test
    public void divTest() {
        expectedException.expect(ArithmeticException.class);
        expectedException.expectMessage("Деление на ноль недопустимо.");
        calc.div(25, 0);
    }

    @AfterClass
    public static void destroy() {
        calc = null;
    }
}
