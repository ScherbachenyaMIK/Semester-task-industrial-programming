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

public class Main {
    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidParameterSpecException, ParserConfigurationException, TransformerException, JAXBException, SAXException, InterruptedException, InvocationTargetException, IllegalAccessException {
        XMLWriter wXML = new XMLWriter("input.xml");
        wXML.WriteString("SADNESSSSSSSSSSaertdstyyuiiijjj");
        wXML.WriteString("Sawerrrrrtes");
        wXML.WriteString("Sawerrrrrtkjes");
        XMLReader rXML = new XMLReader("input.xml");
        String s = rXML.ReadString();
        System.out.println(s);
        wXML.WriteInteger(15);
        int i = rXML.ReadInteger();
        System.out.println(i);
        ArrayList<String> al = new ArrayList<>();
        al.add("x = 0");
        al.add("t = 1.0");
        al.add("y = 1");
        al.add("p = .057");
        al.add("r = -.058d");
        MathExpression me = new MathExpression("x + y + t + p / r = -1", al, 5);
        JsonWriter jw = new JsonWriter("input.json");
        jw.WriteMathExpression(me);
        JsonReader jr = new JsonReader("input.json");
        MathExpression ReadedME;
        ReadedME = jr.ReadMathExpression();
        System.out.println("\nFrom JSON:");
        System.out.println(ReadedME.getExpression());
        ArrayList<Character> variables = ReadedME.getVariables();
        ArrayList<Character> types = ReadedME.getTypes();
        ArrayList<ImmutablePair<Integer, Integer>> integers = ReadedME.getIntegers();
        ArrayList<ImmutablePair<Double, Integer>> doubles = ReadedME.getDoubles();
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
        XMLWriter xw = new XMLWriter("input.xml");
        xw.WriteExpression(me);

        XMLReader xr = new XMLReader("input.xml");
        ReadedME = xr.ReadMathExpression();
        System.out.println("\nFrom XML:");
        System.out.println(ReadedME.getExpression());
        variables = ReadedME.getVariables();
        types = ReadedME.getTypes();
        integers = ReadedME.getIntegers();
        doubles = ReadedME.getDoubles();
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