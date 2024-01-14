import com.udojava.evalex.Expression;
import main.MathExpression;
import main.Result;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MathExpressionTest {

    @Test
    void testResult1() throws IOException {
        String expression = "x + y + t + p / r + g";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("2.0172413793103448", mathExpression.Result(1));
    }

    @Test
    void testResult2() throws IOException {
        String expression = "x + y + t + p / r + g";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("2.017241", mathExpression.Result(2));
    }

    @Test
    void testResult3() throws IOException {
        String expression = "x + y + t + p / r + g";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("2.0172413793103448", mathExpression.Result(3));
    }

    @Test
    void testResult4() throws IOException {
        String expression = "x + y + t";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("2.0", mathExpression.Result(1));
    }

    @Test
    void testResult5() throws IOException {
        String expression = "x + y + t";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("2", mathExpression.Result(2));
    }

    @Test
    void testResult6() throws IOException {
        String expression = "x + y + t";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("2.0", mathExpression.Result(3));
    }

    @Test
    void testResult7() throws IOException {
        String expression = "x";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 5");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("5", mathExpression.Result(1));
    }

    @Test
    void testResult8() throws IOException {
        String expression = "x";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 5");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("5", mathExpression.Result(2));
    }

    @Test
    void testResult9() throws IOException {
        String expression = "x";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 5");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("5.0", mathExpression.Result(3));
    }
    @Test
    void testResult10() throws IOException {
        String expression = "x * z / 5";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 5");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        try {
            mathExpression.Result(1);
            fail();
        }
        catch (NumberFormatException exception) {

        }
    }

    @Test
    void testResult11() throws IOException {
        String expression = "x * z / 5";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 5");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        try {
            mathExpression.Result(2);
            fail();
        }
        catch (Expression.ExpressionException exception) {

        }
    }

    @Test
    void testResult12() throws IOException {
        String expression = "x * z / 5";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 5");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        try {
            mathExpression.Result(3);
            fail();
        }
        catch (IllegalArgumentException exception) {

        }
    }

    @Test
    void testResult13() throws IOException {
        String expression = "4 * 1.0 / 5";
        ArrayList <String> arrayList = new ArrayList<>();
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("0.8", mathExpression.Result(1));
    }

    @Test
    void testResult14() throws IOException {
        String expression = "4 * 1.0 / 5";
        ArrayList <String> arrayList = new ArrayList<>();
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("0.8", mathExpression.Result(2));
    }

    @Test
    void testResult15() throws IOException {
        String expression = "4 * 1.0 / 5";
        ArrayList <String> arrayList = new ArrayList<>();
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("0.8", mathExpression.Result(3));
    }

    @Test
    void testResult16() throws IOException {
        String expression = "x * y";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("-0.6", mathExpression.Result(1));
    }

    @Test
    void testResult17() throws IOException {
        String expression = "x * y";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("-0.6", mathExpression.Result(2));
    }

    @Test
    void testResult18() throws IOException {
        String expression = "x * y";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("-0.6", mathExpression.Result(3));
    }

    @Test
    void testResult19() throws IOException {
        String expression = "(x + y) * 4 - ((z / p) + t)";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("-400001.63330933335", mathExpression.Result(1));
    }

    @Test
    void testResult20() throws IOException {
        String expression = "(x + y) * 4 - ((z / p) + t)";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("-400001.6", mathExpression.Result(2));
    }

    @Test
    void testResult21() throws IOException {
        String expression = "(x + y) * 4 - ((z / p) + t)";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("-400001.63330933335", mathExpression.Result(3));
    }

    @Test
    void testResult22() throws IOException {
        String expression = "(x + y) * 4 - ((z / p) + t))";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        try {
            assertEquals("-400001.63330933335", mathExpression.Result(1));
            fail();
        }
        catch (IOException exception) {

        }
    }

    @Test
    void testResult23() throws IOException {
        String expression = "(x + y) * 4 - ((z / p) + t))";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        try {
            assertEquals("-400001.6", mathExpression.Result(2));
            fail();
        }
        catch (Expression.ExpressionException exception) {

        }
    }

    @Test
    void testResult24() throws IOException {
        String expression = "(x + y) * 4 - ((z / p) + t))";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        try {
            assertEquals("-400001.63330933335", mathExpression.Result(3));
            fail();
        }
        catch (IllegalArgumentException exception) {

        }
    }

    @Test
    void testResult25() throws IOException {
        String expression = "x / y";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = -13");
        arrayList.add("y = 0");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        try {
            assertEquals("INFINITE", mathExpression.Result(1));
            fail();
        }
        catch (IOException exception) {

        }
    }

    @Test
    void testResult26() throws IOException {
        String expression = "x / y";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = -13");
        arrayList.add("y = 0");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        try {
            assertEquals("INFINITE", mathExpression.Result(2));
            fail();
        }
        catch (ArithmeticException exception) {

        }
    }

    @Test
    void testResult27() throws IOException {
        String expression = "x / y";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = -13");
        arrayList.add("y = 0");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        try {
            assertEquals("INFINITE", mathExpression.Result(3));
            fail();
        }
        catch (IllegalArgumentException exception) {

        }
    }

    @Test
    void testToString1() {
        String expression = "x + y + t + p / r + g";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("""
                        expression : x + y + t + p / r + g
                        variables:
                        x (i) = 0;
                        y (i) = 1;
                        t (d) = 1.0;
                        g (i) = 1;
                        p (d) = 0.057;
                        r (d) = -0.058;
                        """,
                mathExpression.toString());
    }

    @Test
    void testToString2() {
        String expression = "x + y + t";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("""
                        expression : x + y + t
                        variables:
                        x (i) = 0;
                        y (i) = 1;
                        t (d) = 1.0;
                        g (i) = 1;
                        p (d) = 0.057;
                        r (d) = -0.058;
                        """,
                mathExpression.toString());
    }

    @Test
    void testToString3() {
        String expression = "x";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 5");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("""
                        expression : x
                        variables:
                        x (i) = 5;
                        """,
                mathExpression.toString());
    }

    @Test
    void testToString4() {
        String expression = "x * z / 5";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 5");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("""
                        expression : x * z / 5
                        variables:
                        x (i) = 5;
                        """,
                mathExpression.toString());
    }

    @Test
    void testToString5() {
        String expression = "4 * 1.0 / 5";
        ArrayList <String> arrayList = new ArrayList<>();
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("""
                        expression : 4 * 1.0 / 5
                        variables:
                        """,
                mathExpression.toString());
    }

    @Test
    void testToString6() {
        String expression = "x * y";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("""
                        expression : x * y
                        variables:
                        x (d) = 6.0E-6;
                        y (d) = -100000.0;
                        """,
                mathExpression.toString());
    }

    @Test
    void testToString7() {
        String expression = "(x + y) * 4 - ((z / p) + t)";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("""
                        expression : (x + y) * 4 - ((z / p) + t)
                        variables:
                        x (d) = 6.0E-6;
                        y (d) = -100000.0;
                        z (i) = -5;
                        p (i) = 3;
                        t (d) = 3.3;
                        """,
                mathExpression.toString());
    }

    @Test
    void testToString8() {
        String expression = "(x + y) * 4 - ((z / p) + t))";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("""
                        expression : (x + y) * 4 - ((z / p) + t))
                        variables:
                        x (d) = 6.0E-6;
                        y (d) = -100000.0;
                        z (i) = -5;
                        p (i) = 3;
                        t (d) = 3.3;
                        """,
                mathExpression.toString());
    }

    @Test
    void testReplaceVariablesWithNumbers1() {
        String expression = "x + y + t + p / r + g";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("(0 + 1 + 1.0 + 0.057 / (-0.058) + 1)", mathExpression.replaceVariablesWithNumbers());
    }

    @Test
    void testReplaceVariablesWithNumbers2() {
        String expression = "x + y + t";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("(0 + 1 + 1.0)", mathExpression.replaceVariablesWithNumbers());
    }

    @Test
    void testReplaceVariablesWithNumbers3() {
        String expression = "x";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 5");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("(5)", mathExpression.replaceVariablesWithNumbers());
    }

    @Test
    void testReplaceVariablesWithNumbers4() {
        String expression = "x * z / 5";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 5");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("(5 * z / 5)", mathExpression.replaceVariablesWithNumbers());
    }

    @Test
    void testReplaceVariablesWithNumbers5() {
        String expression = "4 * 1.0 / 5";
        ArrayList <String> arrayList = new ArrayList<>();
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("(4 * 1.0 / 5)", mathExpression.replaceVariablesWithNumbers());
    }

    @Test
    void testReplaceVariablesWithNumbers6() {
        String expression = "x * y";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("(6.0E-6 * (-100000.0))", mathExpression.replaceVariablesWithNumbers());
    }

    @Test
    void testReplaceVariablesWithNumbers7() {
        String expression = "(x + y) * 4 - ((z / p) + t)";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("((6.0E-6 - 100000.0) * 4 - ((-5 / 3) + 3.3))", mathExpression.replaceVariablesWithNumbers());
    }

    @Test
    void testReplaceVariablesWithNumbers8() {
        String expression = "(x + y) * 4 - ((z / p) + t))";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("((6.0E-6 - 100000.0) * 4 - ((-5 / 3) + 3.3)))", mathExpression.replaceVariablesWithNumbers());
    }

    @Test
    void MathExpressionConstructorTest1() {
        String expression = "(x + y) * 4 - ((z / p) + t))";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        arrayList.add("z = -5");
        arrayList.add("p = 3");
        arrayList.add("t = 3.3");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("(x + y) * 4 - ((z / p) + t))", mathExpression.getExpression());
        ArrayList <Character> variables = new ArrayList<>();
        variables.add('x');
        variables.add('y');
        variables.add('z');
        variables.add('p');
        variables.add('t');
        assertEquals(mathExpression.getVariables(), variables);
        ArrayList <Character> types = new ArrayList<>();
        types.add('d');
        types.add('d');
        types.add('i');
        types.add('i');
        types.add('d');
        assertEquals(mathExpression.getTypes(), types);
        ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
        integers.add(new ImmutablePair<>(-5, 2));
        integers.add(new ImmutablePair<>(3, 3));
        assertEquals(mathExpression.getIntegers(), integers);
        ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
        doubles.add(new ImmutablePair<>(6.0E-6, 0));
        doubles.add(new ImmutablePair<>(-100000.0, 1));
        doubles.add(new ImmutablePair<>(3.3, 4));
        assertEquals(mathExpression.getDoubles(), doubles);
    }
    @Test
    void MathExpressionConstructorTest2() {
        String expression = "x * y";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = 6.0E-6");
        arrayList.add("y = -100000.0");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        assertEquals("x * y", mathExpression.getExpression());
        ArrayList <Character> variables = new ArrayList<>();
        variables.add('x');
        variables.add('y');
        assertEquals(mathExpression.getVariables(), variables);
        ArrayList <Character> types = new ArrayList<>();
        types.add('d');
        types.add('d');
        assertEquals(mathExpression.getTypes(), types);
        ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
        assertEquals(mathExpression.getIntegers(), integers);
        ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
        doubles.add(new ImmutablePair<>(6.0E-6, 0));
        doubles.add(new ImmutablePair<>(-100000.0, 1));
        assertEquals(mathExpression.getDoubles(), doubles);
    }
    @Test
    void MathExpressionConstructorTest3() {
        String expression = "x * y";
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = asder");
        arrayList.add("y = -100000.0");
        try {
            MathExpression mathExpression = new MathExpression(expression, arrayList);
            fail();
        }
        catch (IllegalArgumentException exception) {

        }
    }
    @Test
    void MathExpressionConstructorTest4() {
        String expression = null;
        ArrayList <String> arrayList = new ArrayList<>();
        arrayList.add("x = -5.0");
        arrayList.add("y = -100000.0");
        try {
            MathExpression mathExpression = new MathExpression(expression, arrayList);
            fail();
        }
        catch (IllegalArgumentException exception) {

        }
    }
}

class RegexCalculatorTest {
    Class<?> RegexCalculator = Class.forName("main.MathExpression$RegexCalculator");
    Constructor<?> constructor = RegexCalculator.getDeclaredConstructor(String.class);
    Method summation = RegexCalculator.getDeclaredMethod("Summation", String.class);
    Method subtraction = RegexCalculator.getDeclaredMethod("Subtraction", String.class);
    Method multiplication = RegexCalculator.getDeclaredMethod("Multiplication", String.class);
    Method division = RegexCalculator.getDeclaredMethod("Division", String.class);
    RegexCalculatorTest() throws NoSuchMethodException, ClassNotFoundException {
        constructor.setAccessible(true);
        summation.setAccessible(true);
        subtraction.setAccessible(true);
        multiplication.setAccessible(true);
        division.setAccessible(true);
    }
    public static Object getResultValue(Object obj) throws Exception{
        Class<?> calculator = obj.getClass();
        Field field = calculator.getDeclaredField("result");
        field.setAccessible(true);
        return field.get(obj);
    }

    @Test
    void CommonTest1() throws Exception {
        String expression = "(((54 - 34) + 5) / 5 / 5 * 6)";
        Object calculator = constructor.newInstance(expression);
        assertEquals("6.0", getResultValue(calculator));
    }

    @Test
    void CommonTest2() throws Exception {
        String expression = "((13 * 7 - (14 / 8 + 102)) * 103 - 666)";
        Object calculator = constructor.newInstance(expression);
        assertEquals("-1979.25", getResultValue(calculator));
    }

    @Test
    void CommonTest3() throws Exception {
        String expression = "(1e9 + 7 - 1e5)";
        Object calculator = constructor.newInstance(expression);
        assertEquals("9.99900007E8", getResultValue(calculator));
    }

    @Test
    void CommonTest4() throws Exception {
        String expression = "(((54 - 34) + 5) / 5 / 5 * 6)((((((((";
        try {
            Object calculator = constructor.newInstance(expression);
            fail();
        }
        catch (Exception exception) {

        }
    }

    @Test
    void SummationTest1() throws Exception {
        String expression = "15 + 6 + 78 + 4";
        assertEquals("103.0", ((String) summation.invoke(null, expression)).trim());
    }

    @Test
    void SummationTest2() throws Exception {
        String expression = "15";
        assertEquals("15", ((String) summation.invoke(null, expression)).trim());
    }

    @Test
    void SummationTest3() throws Exception {
        String expression = "13 + 14 + 55";
        assertEquals("82.0", ((String) summation.invoke(null, expression)).trim());
    }

    @Test
    void SummationTest4() throws Exception {
        String expression = "11 + 4 -";
        assertEquals("15.0 -", ((String) summation.invoke(null, expression)).trim());
    }

    @Test
    void SubtractionTest1() throws Exception {
        String expression = "15 + 6 + 78 + 4";
        assertEquals("15 + 6 + 78 + 4", ((String) subtraction.invoke(null, expression)).trim());
    }

    @Test
    void SubtractionTest2() throws Exception {
        String expression = "15";
        assertEquals("15", ((String) subtraction.invoke(null, expression)).trim());
    }

    @Test
    void SubtractionTest3() throws Exception {
        String expression = "13 + 14 + 55 - 44.6 + 4 - -5";
        assertEquals("13 + 14 + 10.399999999999999 + 9.0", ((String) subtraction.invoke(null, expression)).trim());
    }

    @Test
    void SubtractionTest4() throws Exception {
        String expression = "11 + 4 - 555 - 4 - 14 + 3 - 5";
        assertEquals("11 + -569.0 + -2.0", ((String) subtraction.invoke(null, expression)).trim());
    }

    @Test
    void MultiplicationTest1() throws Exception {
        String expression = "15 - 6 + 78 - 4";
        assertEquals("15 - 6 + 78 - 4", ((String) multiplication.invoke(null, expression)).trim());
    }

    @Test
    void MultiplicationTest2() throws Exception {
        String expression = "15";
        assertEquals("15", ((String) multiplication.invoke(null, expression)).trim());
    }

    @Test
    void MultiplicationTest3() throws Exception {
        String expression = "13 + 14 + 55 - 44.6 + 4 * 3 - 7 * -3";
        assertEquals("13 + 14 + 55 - 44.6 + 12.0 - -21.0", ((String) multiplication.invoke(null, expression)).trim());
    }

    @Test
    void MultiplicationTest4() throws Exception {
        String expression = "14 * 5 * 3.3 * 0.5";
        assertEquals("115.5", ((String) multiplication.invoke(null, expression)).trim());
    }

    @Test
    void DivisionTest1() throws Exception {
        String expression = "15 - 6 + 78 - 4 * 2";
        assertEquals("15 - 6 + 78 - 4 * 2", ((String) division.invoke(null, expression)).trim());
    }

    @Test
    void DivisionTest2() throws Exception {
        String expression = "15";
        assertEquals("15", ((String) division.invoke(null, expression)).trim());
    }

    @Test
    void DivisionTest3() throws Exception {
        String expression = "13 - 44.6 + 4 * 3 - 7 + 2 / 2 - 4 / -2";
        assertEquals("13 - 44.6 + 4 * 3 - 7 + 1.0 - -2.0", ((String) division.invoke(null, expression)).trim());
    }

    @Test
    void DivisionTest4() throws Exception {
        String expression = "14 + 10 / 3 * 5 * 3.3 * 0.5 - 14.5 / -13.6";
        assertEquals("14 + 3.3333333333333335 * 5 * 3.3 * 0.5 - -1.0661764705882353", ((String) division.invoke(null, expression)).trim());
    }

    @Test
    void DivisionTest5() throws Exception {
        String expression = "5 / 0";
        try {
            assertEquals("INFINITE", ((String) division.invoke(null, expression)).trim());
            fail();
        }
        catch (Exception exception) {

        }
    }
}

class APICalculatorTest {
    Class<?> RegexCalculator = Class.forName("main.MathExpression$APICalculator");
    Constructor<?> constructor = RegexCalculator.getDeclaredConstructor(String.class);
    APICalculatorTest() throws NoSuchMethodException, ClassNotFoundException {
        constructor.setAccessible(true);
    }
    public static Object getResultValue(Object obj) throws Exception{
        Class<?> calculator = obj.getClass();
        Field field = calculator.getDeclaredField("result");
        field.setAccessible(true);
        return field.get(obj);
    }

    @Test
    void CommonTest1() throws Exception {
        String expression = "(((54 - 34) + 5) / 5 / 5 * 6)";
        Object calculator = constructor.newInstance(expression);
        assertEquals("6", getResultValue(calculator));
    }

    @Test
    void CommonTest2() throws Exception {
        String expression = "((13 * 7 - (14 / 8 + 102)) * 103 - 666)";
        Object calculator = constructor.newInstance(expression);
        assertEquals("-1979.25", getResultValue(calculator));
    }

    @Test
    void CommonTest3() throws Exception {
        String expression = "(1e9 + 7 - 1e5)";
        Object calculator = constructor.newInstance(expression);
        assertEquals("9.999E+8", getResultValue(calculator));
    }

    @Test
    void CommonTest4() throws Exception {
        String expression = "(((54 - 34) + 5) / 5 / 5 * 6)((((((((";
        try {
            Object calculator = constructor.newInstance(expression);
            fail();
        }
        catch (Exception exception) {

        }
    }
    @Test
    void CommonTest5() throws Exception {
        String expression = "5 / 0";
        try {
            Object calculator = constructor.newInstance(expression);
            fail();
        }
        catch (Exception exception) {

        }
    }
}

class ReversivePolishNotationCalculatorTest {
    Class<?> RegexCalculator = Class.forName("main.MathExpression$ReversivePolishNotationCalculator");
    Constructor<?> constructor = RegexCalculator.getDeclaredConstructor(String.class);
    Method toRPN = RegexCalculator.getDeclaredMethod("toRPN", String.class);
    Method isOperator = RegexCalculator.getDeclaredMethod("isOperator", Character.TYPE);
    Method precedence = RegexCalculator.getDeclaredMethod("precedence", Character.TYPE);
    Method calculateRPN = RegexCalculator.getDeclaredMethod("calculateRPN", String.class);
    Method isNumeric = RegexCalculator.getDeclaredMethod("isNumeric", String.class);
    Method performOperation = RegexCalculator.getDeclaredMethod("performOperation", Character.TYPE, Double.TYPE, Double.TYPE);
    ReversivePolishNotationCalculatorTest() throws NoSuchMethodException, ClassNotFoundException {
        constructor.setAccessible(true);
        toRPN.setAccessible(true);
        isOperator.setAccessible(true);
        precedence.setAccessible(true);
        calculateRPN.setAccessible(true);
        isNumeric.setAccessible(true);
        performOperation.setAccessible(true);
    }
    public static Object getResultValue(Object obj) throws Exception{
        Class<?> calculator = obj.getClass();
        Field field = calculator.getDeclaredField("result");
        field.setAccessible(true);
        return field.get(obj);
    }

    @Test
    void CommonTest1() throws Exception {
        String expression = "(((54 - 34) + 5) / 5 / 5 * 6)";
        Object calculator = constructor.newInstance(expression);
        assertEquals("6.0", getResultValue(calculator));
    }

    @Test
    void CommonTest2() throws Exception {
        String expression = "((13 * 7 - (14 / 8 + 102)) * 103 - 666)";
        Object calculator = constructor.newInstance(expression);
        assertEquals("-1979.25", getResultValue(calculator));
    }

    @Test
    void CommonTest3() throws Exception {
        String expression = "(1e9 + 7 - 1e5)";
        Object calculator = constructor.newInstance(expression);
        assertEquals("9.99900007E8", getResultValue(calculator));
    }

    @Test
    void CommonTest4() throws Exception {
        String expression = "(((54 - 34) + 5) / 5 / 5 * 6)((((((((";
        try {
            Object calculator = constructor.newInstance(expression);
            fail();
        }
        catch (Exception exception) {

        }
    }

    @Test
    void toRPNTest1() throws Exception {
        String expression = "(((54 - 34) + 5) / 5 / 5 * 6)";
        assertEquals("54 34 - 5 + 5 / 5 / 6 *", toRPN.invoke(null, expression));
    }

    @Test
    void toRPNTest2() throws Exception {
        String expression = "((13 * 7 - (14 / 8 + 102)) * 103 - 666)";
        assertEquals("13 7 * 14 8 / 102 + - 103 * 666 -", toRPN.invoke(null, expression));
    }

    @Test
    void toRPNTest3() throws Exception {
        String expression = "(1e9 + 7 - 1e5)";
        assertEquals("1e9 7 + 1e5 -", toRPN.invoke(null, expression));
    }

    @Test
    void toRPNTest4() throws Exception {
        String expression = "(((54 - 34) + 5) / 5 / 5 * 6)((((((((";
        try {
            assertEquals("54 34 - 5 + 5 / 5 / 6 *", toRPN.invoke(null, expression));
            fail();
        }
        catch (Exception exception) {

        }
    }

    @Test
    void IsOperatorTest() throws Exception {
        assertTrue((boolean) isOperator.invoke(null, '+'));
        assertTrue((boolean) isOperator.invoke(null, '-'));
        assertTrue((boolean) isOperator.invoke(null, '/'));
        assertTrue((boolean) isOperator.invoke(null, '*'));
        assertFalse((boolean) isOperator.invoke(null, 'c'));
        assertFalse((boolean) isOperator.invoke(null, 'a'));
        assertFalse((boolean) isOperator.invoke(null, 'B'));
        assertFalse((boolean) isOperator.invoke(null, 'R'));
        assertFalse((boolean) isOperator.invoke(null, '5'));
    }

    @Test
    void PrecedenceTest() throws Exception {
        assertEquals(1, precedence.invoke(null, '+'));
        assertEquals(1, precedence.invoke(null, '-'));
        assertEquals(2, precedence.invoke(null, '/'));
        assertEquals(2, precedence.invoke(null, '*'));
        assertEquals(0, precedence.invoke(null, 'c'));
        assertEquals(0, precedence.invoke(null, 'a'));
        assertEquals(0, precedence.invoke(null, 'B'));
        assertEquals(0, precedence.invoke(null, 'R'));
        assertEquals(0, precedence.invoke(null, '5'));
    }

    @Test
    void calculateRPNTest1() throws Exception {
        String expression = "54 34 - 5 + 5 / 5 / 6 *";
        assertEquals(6.0, calculateRPN.invoke(null, expression));
    }

    @Test
    void calculateRPNTest2() throws Exception {
        String expression = "13 7 * 14 8 / 102 + - 103 * 666 -";
        assertEquals(-1979.25, calculateRPN.invoke(null, expression));
    }

    @Test
    void calculateRPNTest3() throws Exception {
        String expression = "1e9 7 + 1e5 -";
        assertEquals(9.99900007E8, calculateRPN.invoke(null, expression));
    }

    @Test
    void calculateRPNTest4() throws Exception {
        String expression = "54 34 - 5 + 5 / 5 / 6 * * +";
        try {
            assertEquals(6.0, calculateRPN.invoke(null, expression));
            fail();
        }
        catch (Exception exception) {

        }
    }

    @Test
    void calculateRPNTest5() throws Exception {
        String expression = "-5 0 /";
        try {
            assertEquals("INFINITE", calculateRPN.invoke(null, expression));
            fail();
        }
        catch (Exception exception) {
        }
    }

    @Test
    void isNumericTest() throws Exception {
        assertTrue((boolean) isNumeric.invoke(null, "1.0"));
        assertTrue((boolean) isNumeric.invoke(null, "-0.5"));
        assertTrue((boolean) isNumeric.invoke(null, "-.8"));
        assertTrue((boolean) isNumeric.invoke(null, "1e-6"));
        assertFalse((boolean) isNumeric.invoke(null, "c"));
        assertTrue((boolean) isNumeric.invoke(null, "5"));
        assertTrue((boolean) isNumeric.invoke(null, "588"));
        assertFalse((boolean) isNumeric.invoke(null, "dawe"));
        assertFalse((boolean) isNumeric.invoke(null, "-6 + 4"));
    }

    @Test
    void performOperationTest1() throws Exception {
        assertEquals(-1.0, performOperation.invoke(null, '+', -5, 4));
    }

    @Test
    void performOperationTest2() throws Exception {
        assertEquals(-20.0, performOperation.invoke(null, '*', 5, -4));
    }

    @Test
    void performOperationTest3() throws Exception {
        assertEquals(1.0, performOperation.invoke(null, '-', -4, -5));
    }

    @Test
    void performOperationTest4() throws Exception {
        assertEquals(-0.8, performOperation.invoke(null, '/', -4, 5));
    }

    @Test
    void performOperationTest5() throws Exception {
        try {
            assertEquals(-0.8, performOperation.invoke(null, 'v', -4, 5));
            fail();
        }
        catch (Exception exception) {

        }
    }

    @Test
    void performOperationTest6() throws Exception {
        try {
            assertEquals("INFINITE", performOperation.invoke(null, '/', 1000, 0));
            fail();
        }
        catch (Exception exception) {

        }
    }
}

class ResultTest {
    @Test
    void ConstructorTest1() {
        Result result = new Result("-464");
        assertEquals("-464", result.getResult());
    }
    @Test
    void ConstructorTest2() {
        Result result = new Result("-11.54648");
        assertEquals("-11.54648", result.getResult());
    }
    @Test
    void ConstructorTest3() {
        Result result = new Result();
        assertEquals("", result.getResult());
    }
    @Test
    void ConstructorTest4() {
        Result result = new Result('e');
        assertEquals("error!", result.getResult());
    }
}