import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.udojava.evalex.Expression;

@Setter
@Getter
public class MathExpression {
    private String expression;
    private ArrayList<Character> variables = new ArrayList<>();
    private ArrayList<Character> types = new ArrayList<>();
    private ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
    private ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    static Pattern regex_double = Pattern.compile(" -?(\\d+\\.\\d*[dD]?$|\\.\\d+[dD]?$|[1-9]\\.?\\d*?[eE]-?[1-9]\\d*[dD]?$)");
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    static Pattern regex_int = Pattern.compile(" -?\\d+$");
    MathExpression() {
        expression = "";
    }
    MathExpression(String expression_, ArrayList<String> variables_) throws IllegalArgumentException {
        if (expression_ == null) {
            throw new IllegalArgumentException("Expression is null");
        }
        expression = expression_;
        for(String i : variables_)
        {
            variables.add(i.charAt(0));
            Matcher matcher = regex_int.matcher(i);
            if(matcher.find())
            {
                types.add('i');
                ImmutablePair<Integer, Integer> pair = new ImmutablePair<>(Integer.parseInt(i.substring(matcher.start() + 1)),
                        variables.size() - 1);
                integers.add(pair);
            }
            else {
                matcher = regex_double.matcher(i);
                if (matcher.find()) {
                    types.add('d');
                    ImmutablePair<Double, Integer> pair = new ImmutablePair<>(Double.parseDouble(i.substring(matcher.start() + 1)),
                            variables.size() - 1);
                    doubles.add(pair);
                } else {
                    throw new IllegalArgumentException("Variables does not match the format");
                }
            }
        }
    }
    String replaceVariablesWithNumbers()
    {
        String new_expression = expression;
        for (int i = 0; i < variables.size(); ++i)
        {
            char var = variables.get(i);
            if (types.get(i) == 'i')
            {
                int j;
                for(j = 0; integers.get(j).getRight() != i; ++j);
                int value = integers.get(j).getLeft();
                new_expression = new_expression.replaceAll(String.valueOf(var), String.valueOf(value));
            }
            else
            {
                int j;
                for(j = 0; doubles.get(j).getRight() != i; ++j);
                double value = doubles.get(j).getLeft();
                new_expression = new_expression.replaceAll(String.valueOf(var), String.valueOf(value));
            }
        }
        new_expression = new_expression.replaceAll("[dD]","");
        new_expression = new_expression.replaceAll("\\+ -", "- ");
        new_expression = new_expression.replaceAll("- -", "+ ");
        new_expression = new_expression.replaceAll("\\* (-\\d+\\.?\\d*[Dd]?|-\\d*\\.\\d+[Dd]?|[1-9][eE]-?[1-9]\\d*[dD]?)", "\\* \\($1\\) ");
        new_expression = new_expression.replaceAll("/ (-\\d+\\.?\\d*[Dd]?|-\\d*\\.\\d+[Dd]?|[1-9][eE]-?[1-9]\\d*[dD]?)", "/ \\($1\\) ");
        new_expression = new_expression.replaceAll("  ", " ");
        new_expression = "(" + new_expression.trim() + ")";
        return new_expression;
    }
    public String Result(int type) throws IOException {
        switch (type) {
            case 1:
                return new RegexCalculator(replaceVariablesWithNumbers()).getResult();
            case 2:
                return new APICalculator(replaceVariablesWithNumbers()).getResult();
            case 3:
                return new ReversivePolishNotationCalculator(replaceVariablesWithNumbers()).getResult();
        }
        throw new IllegalArgumentException("Invalid argument");
    }
    public String toString() {
        StringBuilder result = new StringBuilder("expression : " + expression + "\nvariables:\n");
        for(int i = 0; i < variables.size(); ++i) {
            result.append(variables.get(i) + " (" +
                    types.get(i) + ") = ");
            if (types.get(i) == 'i') {
                for (var t : integers) {
                    if (t.getRight() == i)
                        result.append(t.getLeft() + ";\n");
                }
            } else {
                for (var t : doubles) {
                    if (t.getRight() == i)
                        result.append(t.getLeft() + ";\n");
                }
            }
        }
        return result.toString();
    }
    private static class RegexCalculator
    {
        static Pattern brackets_regex = Pattern.compile("\\(([^(]*?)\\)");
        static Pattern negative_number_regex = Pattern.compile("-[^ -+*/]+");
        static Pattern multiplication_regex = Pattern.compile("( |^)(-?[^- +/*]+|-?[^- +/*]+E-?[^- +/*]+) \\* (.+?)( |$)");
        static Pattern division_regex = Pattern.compile("( |^)(-?[^- +/*]+|-?[^- +/*]+E-?[^- +/*]+) / (.+?)( |$)");
        static Pattern summation_regex = Pattern.compile("( |^)(-?[^- +/*]+|-?[^- +/*]+E-?[^- +/*]+) \\+ (.+?)( |$)");
        static Pattern subtraction_regex = Pattern.compile("( |^)(-?[^- +/*]+|-?[^- +/*]+E-?[^- +/*]+) - (.+?)( |$)");
        @Getter
        String result;
        RegexCalculator(String expression) throws IOException {
            Matcher brackets_matcher = brackets_regex.matcher(expression);
            while (brackets_matcher.find())
            {
                String brackets = brackets_matcher.group(1);
                if (brackets.matches("-?(\\d+\\.\\d*[dD]?$|\\.\\d+[dD]?$|[1-9]\\.?\\d*?[eE]-?[1-9]\\d*[dD]?$)") ||
                        brackets.matches("-?\\d+$")) {
                    expression = brackets_matcher.replaceFirst(brackets);
                    brackets_matcher = brackets_regex.matcher(expression);
                    continue;
                }
                if (brackets.matches(negative_number_regex.toString())) {
                    expression = brackets_matcher.replaceFirst(brackets);
                    brackets_matcher = brackets_regex.matcher(expression);
                    continue;
                }
                brackets = Division(brackets).trim();
                brackets = Multiplication(brackets).trim();
                brackets = Subtraction(brackets).trim();
                brackets = Summation(brackets).trim();
                expression = brackets_matcher.replaceFirst(brackets);
                brackets_matcher = brackets_regex.matcher(expression);
            }
            if (expression.matches("^ ?-?(\\d+\\.\\d*[dD]?|\\.\\d+[dD]?|[1-9]\\.?\\d*?[eE]-?[1-9]\\d*[dD]?) ?$")
                || expression.matches("^ ?-?\\d+ ?$")) {
                result = expression;
            }
            else {
                throw new IOException ("Incorrect expression");
            }
        }
        private static String Multiplication(String brackets) {
            Matcher multiplication_matcher = multiplication_regex.matcher(brackets);
            while (multiplication_matcher.find()) {
                Double left_operand = Double.parseDouble(multiplication_matcher.group(2));
                Double right_operand = Double.parseDouble(multiplication_matcher.group(3));
                brackets = multiplication_matcher.replaceFirst(" " + Double.valueOf(left_operand * right_operand).toString() + " ");
                multiplication_matcher = multiplication_regex.matcher(brackets);
            }
            return brackets;
        }
        private static String Division(String brackets) throws IOException {
            Matcher division_matcher = division_regex.matcher(brackets);
            while (division_matcher.find()) {
                Double left_operand = Double.parseDouble(division_matcher.group(2));
                Double right_operand = Double.parseDouble(division_matcher.group(3));
                if (Math.abs(right_operand) < 1e-15) {
                    throw new IOException ("Division by zero");
                }
                brackets = division_matcher.replaceFirst(" " + Double.valueOf(left_operand / right_operand).toString() + " ");
                division_matcher = division_regex.matcher(brackets);
            }
            return brackets;
        }
        private static String Summation(String brackets) {
            Matcher summation_matcher = summation_regex.matcher(brackets);
            while (summation_matcher.find()) {
                Double left_operand = Double.parseDouble(summation_matcher.group(2));
                Double right_operand = Double.parseDouble(summation_matcher.group(3));
                brackets = summation_matcher.replaceFirst(" " + Double.valueOf(left_operand + right_operand).toString() + " ");
                summation_matcher = summation_regex.matcher(brackets);
            }
            return brackets;
        }
        private static String Subtraction(String brackets) {
            Matcher subtraction_matcher = subtraction_regex.matcher(brackets);
            while (subtraction_matcher.find()) {
                Double left_operand = Double.parseDouble(subtraction_matcher.group(2));
                Double right_operand = Double.parseDouble(subtraction_matcher.group(3));
                brackets = subtraction_matcher.replaceFirst(" " + Double.valueOf(left_operand - right_operand).toString() + " ");
                subtraction_matcher = subtraction_regex.matcher(brackets);
            }
            return brackets;
        }
    }
    private static class ReversivePolishNotationCalculator
    {
        @Getter
        private String result;
        ReversivePolishNotationCalculator(String expression) {
            expression = toRPN(expression);
            result = Double.toString(calculateRPN(expression));
        }
        private static String toRPN(String expression) {
            StringBuilder result = new StringBuilder();
            Deque<Character> stack = new ArrayDeque<>();

            for (int i = 0; i < expression.length(); i++) {
                char token = expression.charAt(i);

                if (Character.isDigit(token) || token == '.') {
                    int j = i;
                    while (j < expression.length() && (Character.isDigit(expression.charAt(j))
                            || expression.charAt(j) == '.' || expression.charAt(j) == 'E' || expression.charAt(j) == 'e'
                            || expression.charAt(j) == '+' || expression.charAt(j) == '-')) {
                        j++;
                    }
                    result.append(expression.substring(i, j)).append(" ");
                    i = j - 1;
                } else if (token == '(') {
                    stack.push(token);
                } else if (token == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        result.append(stack.pop()).append(" ");
                    }
                    if (!stack.isEmpty() && stack.peek() == '(') {
                        stack.pop();
                    } else {
                        throw new IllegalArgumentException("Invalid expression - mismatched parentheses");
                    }
                } else if (isOperator(token)) {
                    if (token == '-' && (i == 0 || expression.charAt(i - 1) == '(' || isOperator(expression.charAt(i - 1)))) {
                        result.append("0 ");
                    } else {
                        while (!stack.isEmpty() && precedence(token) <= precedence(stack.peek())) {
                            result.append(stack.pop()).append(" ");
                        }
                    }
                    stack.push(token);
                }
            }

            while (!stack.isEmpty()) {
                if (stack.peek() == '(') {
                    throw new IllegalArgumentException("Invalid expression - mismatched parentheses");
                }
                result.append(stack.pop()).append(" ");
            }

            return result.toString().trim();
        }

        private static boolean isOperator(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/';
        }

        private static int precedence(char operator) {
            if (operator == '+' || operator == '-') {
                return 1;
            } else if (operator == '*' || operator == '/') {
                return 2;
            }
            return 0;
        }

        public static double calculateRPN(String rpnExpression) {
            Deque<Double> stack = new ArrayDeque<>();
            String[] tokens = rpnExpression.split("\\s+");

            for (String token : tokens) {
                if (token.isEmpty()) {
                    continue;
                } else if (isNumeric(token)) {
                    stack.push(Double.parseDouble(token));
                } else if (isOperator(token.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Invalid expression: insufficient operands");
                    }
                    double operand2 = stack.pop();
                    double operand1 = stack.pop();
                    double result = performOperation(token.charAt(0), operand1, operand2);
                    stack.push(result);
                } else {
                    throw new IllegalArgumentException("Invalid token in RPN expression: " + token);
                }
            }

            if (stack.size() != 1) {
                throw new IllegalArgumentException("Invalid expression: too many operands");
            }

            return stack.pop();
        }

        private static boolean isNumeric(String str) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private static double performOperation(char operator, double operand1, double operand2) {
            switch (operator) {
                case '+':
                    return operand1 + operand2;
                case '-':
                    return operand1 - operand2;
                case '*':
                    return operand1 * operand2;
                case '/':
                    if (operand2 == 0) {
                        throw new IllegalArgumentException("Division by zero");
                    }
                    return operand1 / operand2;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        }
    }
    private static class APICalculator
    {
        @Getter
        private String result;
        APICalculator(String expression) {
            Expression expr = new Expression(expression);
            result = expr.eval().toString();
        }
    }
}

class Result {
    @Getter
    private String result;
    Result() {
        result = "";
    }
    Result(char e) {
        result = "error!";
    }
    Result(String result_) {
        result = result_;
    }
}