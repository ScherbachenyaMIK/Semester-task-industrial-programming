import java.io.IOException;
import java.util.ArrayList;
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
    MathExpression(String expession_, ArrayList<String> variables_) throws IllegalArgumentException {
        expression = expession_;
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
    private String replaceVariablesWithNumbers()
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
        new_expression = new_expression.replaceAll("\\* (-\\d+\\.?\\d*[Dd]?|-\\d*\\.\\d+[Dd]?|[1-9][eE]-?[1-9]\\d*[dD]?) ", "\\* \\($1\\) ");
        new_expression = new_expression.replaceAll("/ (-\\d+\\.?\\d*[Dd]?|-\\d*\\.\\d+[Dd]?|[1-9][eE]-?[1-9]\\d*[dD]?) ", "/ \\($1\\) ");
        if (!(new_expression.startsWith("(") && new_expression.endsWith(")"))) {
            new_expression = "(" + new_expression + ")";
        }
        return new_expression;
    }
    public String Result(int type) throws IOException {
        switch (type) {
            case 1:
                return new RegexCalculator(replaceVariablesWithNumbers()).getResult();
            case 2:
                return new APICalculator(replaceVariablesWithNumbers()).getResult();
            case 3:
                break;
        }
        throw new IllegalArgumentException("Invalid arguemnt");
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
    private class RegexCalculator
    {
        static Pattern brackets_regex = Pattern.compile("\\(([^(]*?)\\)");
        static Pattern negative_number_regex = Pattern.compile("-[^-+*/]+");
        static Pattern multiplication_regex = Pattern.compile("( |^)([^+^-^/^\\\\]+) \\* (.+?)( |$)");
        static Pattern division_regex = Pattern.compile("( |^)([^+^-^/^\\\\]+) / (.+?)( |$)");
        static Pattern summation_regex = Pattern.compile("( |^)([^+^-^/^\\\\]+) \\+ (.+?)( |$)");
        static Pattern subtraction_regex = Pattern.compile("( |^)([^+^-^/^\\\\]+) - (.+?)( |$)");
        @Getter
        String result;
        RegexCalculator(String expression) {
            Matcher brackets_matcher = brackets_regex.matcher(expression);
            while (brackets_matcher.find())
            {
                String brackets = brackets_matcher.group(1);
                if (brackets.matches(negative_number_regex.toString())) {
                    expression = brackets_matcher.replaceFirst(brackets);
                    brackets_matcher = brackets_regex.matcher(expression);
                    continue;
                }
                brackets = Multiple(brackets);
                brackets = Division(brackets);
                brackets = Summation(brackets);
                brackets = Subtraction(brackets);
                expression = brackets_matcher.replaceFirst(brackets);
                brackets_matcher = brackets_regex.matcher(expression);
            }
            result = expression.substring(1, expression.length() - 1);
        }
        private String Multiple(String brackets) {
            Matcher multiplication_matcher = multiplication_regex.matcher(brackets);
            while (multiplication_matcher.find()) {
                Double left_operand = Double.parseDouble(multiplication_matcher.group(2));
                Double right_operand = Double.parseDouble(multiplication_matcher.group(3));
                brackets = multiplication_matcher.replaceFirst(" " + Double.valueOf(left_operand * right_operand).toString() + " ");
                multiplication_matcher = multiplication_regex.matcher(brackets);
            }
            return brackets;
        }
        private String Division(String brackets) {
            Matcher division_matcher = division_regex.matcher(brackets);
            while (division_matcher.find()) {
                Double left_operand = Double.parseDouble(division_matcher.group(2));
                Double right_operand = Double.parseDouble(division_matcher.group(3));
                brackets = division_matcher.replaceFirst(" " + Double.valueOf(left_operand / right_operand).toString() + " ");
                division_matcher = division_regex.matcher(brackets);
            }
            return brackets;
        }
        private String Summation(String brackets) {
            Matcher summation_matcher = summation_regex.matcher(brackets);
            while (summation_matcher.find()) {
                Double left_operand = Double.parseDouble(summation_matcher.group(2));
                Double right_operand = Double.parseDouble(summation_matcher.group(3));
                brackets = summation_matcher.replaceFirst(" " + Double.valueOf(left_operand + right_operand).toString() + " ");
                summation_matcher = summation_regex.matcher(brackets);
            }
            return brackets;
        }
        private String Subtraction(String brackets) {
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
    //TODO Calculator
    private class ReversivePolishNotationCalculator
    {

    }
    private class APICalculator
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