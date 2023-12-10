package org.example;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.xml.sax.SAXException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;

//TODO Refactor code
//TODO HANDLE Exceptions
//TODO Intefaces
//TODO Unit-tests
//TODO Design patterns
public class Main {
    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidParameterSpecException, ParserConfigurationException, TransformerException, JAXBException, SAXException, InterruptedException, InvocationTargetException, IllegalAccessException {
        ArrayList<MathExpression> mel = new ArrayList<>();
        ArrayList<String> al = new ArrayList<>();
        al.add("x = 0");
        al.add("y = 1");
        al.add("t = 1.0");
        al.add("g = 1");
        al.add("p = .057");
        al.add("r = -.058d");
        mel.add(new MathExpression("x + y + t + p / r + g", al));
        al = new ArrayList<>();
        al.add("x = 6");
        al.add("y = 1");
        al.add("t = 40");
        al.add("p = 1.057");
        al.add("r = -.058d");
        mel.add(new MathExpression("x + y + t + p / r", al));
        al = new ArrayList<>();
        al.add("x = 6e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        JsonWriter jw = new JsonWriter("input.json");
        jw.WriteListOfMathExpressions(mel);
        JsonNonAPIReader jr = new JsonNonAPIReader("input.json");
        ArrayList<MathExpression> ReadedMEs;
        ReadedMEs = jr.ReadListOfMathExpressions();
        jr.CloseJsonNonAPIReader();
        System.out.println("\nFrom json:");
        System.out.println(ReadedMEs.get(0).getExpression());
        ArrayList<Character> variables = ReadedMEs.get(0).getVariables();
        ArrayList<Character> types = ReadedMEs.get(0).getTypes();
        ArrayList<ImmutablePair<Integer, Integer>> integers = ReadedMEs.get(0).getIntegers();
        ArrayList<ImmutablePair<Double, Integer>> doubles = ReadedMEs.get(0).getDoubles();
        for(Character c : variables)
        {
            System.out.print(c + " ");
        }
        System.out.println();
        for(Character c : types)
        {
            System.out.print(c + " ");
        }
        System.out.println();
        for(ImmutablePair<Integer, Integer> p : integers)
        {
            System.out.print(p + " ");
        }
        System.out.println();
        for(ImmutablePair<Double, Integer> p : doubles)
        {
            System.out.print(p + " ");
        }
        System.out.println();
        System.out.println(ReadedMEs.get(1).getExpression());
        variables = ReadedMEs.get(1).getVariables();
        types = ReadedMEs.get(1).getTypes();
        integers = ReadedMEs.get(1).getIntegers();
        doubles = ReadedMEs.get(1).getDoubles();
        for(Character c : variables)
        {
            System.out.print(c + " ");
        }
        System.out.println();
        for(Character c : types)
        {
            System.out.print(c + " ");
        }
        System.out.println();
        for(ImmutablePair<Integer, Integer> p : integers)
        {
            System.out.print(p + " ");
        }
        System.out.println();
        for(ImmutablePair<Double, Integer> p : doubles)
        {
            System.out.print(p + " ");
        }
        System.out.println();
        System.out.println(ReadedMEs.get(2).getExpression());
        variables = ReadedMEs.get(2).getVariables();
        types = ReadedMEs.get(2).getTypes();
        integers = ReadedMEs.get(2).getIntegers();
        doubles = ReadedMEs.get(2).getDoubles();
        for(Character c : variables)
        {
            System.out.print(c + " ");
        }
        System.out.println();
        for(Character c : types)
        {
            System.out.print(c + " ");
        }
        System.out.println();
        for(ImmutablePair<Integer, Integer> p : integers)
        {
            System.out.print(p + " ");
        }
        System.out.println();
        for(ImmutablePair<Double, Integer> p : doubles)
        {
            System.out.print(p + " ");
        }
    }
}