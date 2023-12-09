package org.example;

import org.xml.sax.SAXException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidParameterSpecException, ParserConfigurationException, TransformerException, JAXBException, SAXException, InterruptedException {
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
        System.out.println(me.getExpession());
        me.replaceVariablesWithNumbers();
        System.out.println(me.getExpession());
        DearchiverRar dr = new DearchiverRar("d:/Archives/Tasks1-2.rar");
        DearchiverZip dz = new DearchiverZip("d:/Archives/Tasks1-2.zip");
        ArrayList<String> files = dz.Dearchive();
        System.out.println("ZipArchiver:");
        for(String it : files)
        {
            System.out.println(it);
        }
        dz.CloseDearchiverZip();
        files = dr.Dearchive();
        System.out.println("\nRarArchiver:");
        for(String it : files)
        {
            System.out.println(it);
        }
        dr.CloseDearchiverRar();
//        ArchiverRar ar = new ArchiverRar("target/");
//        ar.Archive();
    }
}