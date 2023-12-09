package org.example;

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
        al.add("y = 1");
        al.add("t = 1.0");
        al.add("p = .057");
        al.add("r = -.058d");
        MathExpression me = new MathExpression("x + y + t + p / r = -1", al, 5);
        JsonWriter jw = new JsonWriter("input.json");
        jw.WriteMathExpression(me);
        XMLWriter xw = new XMLWriter("input.xml");
        xw.WriteExpression(me);
    }
}