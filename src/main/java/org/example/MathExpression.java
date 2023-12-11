package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;
//TODO Calculator
public class MathExpression {
    @Setter
    @Getter
    private String expression;
    @Setter
    @Getter
    private ArrayList<Character> variables = new ArrayList<>();
    @Setter
    @Getter
    private ArrayList<Character> types = new ArrayList<>();
    @Setter
    @Getter
    private ArrayList<ImmutablePair<Integer, Integer>> integers = new ArrayList<>();
    @Setter
    @Getter
    private ArrayList<ImmutablePair<Double, Integer>> doubles = new ArrayList<>();
    static Pattern regex_double = Pattern.compile(" -?(\\d+\\.\\d*[dD]?$|\\.\\d+[dD]?$|[1-9]\\.?\\d*?[eE]-?[1-9]\\d*[dD]?$)");
    static Pattern regex_int = Pattern.compile(" -?\\d+$");
    MathExpression() {
        expression = "";
    }
    MathExpression(String expession_, ArrayList<String> variables_) throws IOException {
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
                    throw new IOException();
                }
            }
        }
    }
    public void replaceVariablesWithNumbers()
    {
        for (int i = 0; i < variables.size(); ++i)
        {
            char var = variables.get(i);
            if (types.get(i) == 'i')
            {
                int j;
                for(j = 0; integers.get(j).getRight() != i; ++j);
                int value = integers.get(j).getLeft();
                expression = expression.replaceAll(String.valueOf(var), String.valueOf(value));
            }
            else
            {
                int j;
                for(j = 0; doubles.get(j).getRight() != i; ++j);
                double value = doubles.get(j).getLeft();
                expression = expression.replaceAll(String.valueOf(var), String.valueOf(value));
            }
        }
        expression = expression.replaceAll("[dD]","");
        expression = expression.replaceAll("\\+ -", "- ");
        expression = expression.replaceAll("- -", "+ ");
        expression = expression.replaceAll("\\* (-\\d+\\.?\\d*[Dd]?|-\\d*\\.\\d+[Dd]?|[1-9][eE]-?[1-9]\\d*[dD]?) ", "\\* \\($1\\) ");
        expression = expression.replaceAll("/ (-\\d+\\.?\\d*[Dd]?|-\\d*\\.\\d+[Dd]?|[1-9][eE]-?[1-9]\\d*[dD]?) ", "/ \\($1\\) ");
    }
}
