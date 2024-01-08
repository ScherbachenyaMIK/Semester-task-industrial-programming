import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class XMLWriterTests {
    private String outputPath = "TestOutput.xml";

    @Test
    void writeString() throws IOException {
        String str = "AbRAcaDabrA";
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLWriter XW = new XMLWriter(outputPath);
        XW.WriteString(str);
        
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <Data>AbRAcaDabrA</Data>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeInteger() throws IOException {
        int i = 113;
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLWriter XW = new XMLWriter(outputPath);
        XW.WriteInteger(i);
        
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <Data>113</Data>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeMathExpression() throws IOException {
        String expression = "x + y + t + p / r + g";
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLWriter XW = new XMLWriter(outputPath);
        XW.WriteMathExpression(mathExpression);
        
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <MathExpression>
                    <expression>x + y + t + p / r + g</expression>
                    <variables>"x""y""t""g""p""r"</variables>
                    <types>"i""i""d""i""d""d"</types>
                    <integers>"(0,0)""(1,1)""(1,3)"</integers>
                    <doubles>"(1.0,2)""(0.057,4)""(-0.058,5)"</doubles>
                  </MathExpression>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeListOfMathExpressions() throws IOException {
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
        al.add("x = 6.0e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLWriter XW = new XMLWriter(outputPath);
        XW.WriteListOfMathExpressions(mel);
        
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <MathExpression>
                    <expression>x + y + t + p / r + g</expression>
                    <variables>"x""y""t""g""p""r"</variables>
                    <types>"i""i""d""i""d""d"</types>
                    <integers>"(0,0)""(1,1)""(1,3)"</integers>
                    <doubles>"(1.0,2)""(0.057,4)""(-0.058,5)"</doubles>
                  </MathExpression>
                  <MathExpression>
                    <expression>x + y + t + p / r</expression>
                    <variables>"x""y""t""p""r"</variables>
                    <types>"i""i""i""d""d"</types>
                    <integers>"(6,0)""(1,1)""(40,2)"</integers>
                    <doubles>"(1.057,3)""(-0.058,4)"</doubles>
                  </MathExpression>
                  <MathExpression>
                    <expression>x * y</expression>
                    <variables>"x""x""y"</variables>
                    <types>"d""d""d"</types>
                    <integers>""</integers>
                    <doubles>"(6.0E-6,0)""(6.0E-6,1)""(-100000.0,2)"</doubles>
                  </MathExpression>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeResult() throws IOException {
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLWriter XW = new XMLWriter(outputPath);
        XW.WriteResult(new Result('e'));
        
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <Result>
                    <result>error!</result>
                  </Result>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeListOfResultsOfMathExpressions() throws IOException {
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
        al.add("x = 6.0e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        al = new ArrayList<>();
        al.add("x = 6e-6");
        mel.add(new MathExpression("x * y", al));
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLWriter XW = new XMLWriter(outputPath);
        XW.WriteListOfResultsOfMathExpressions(mel, 2);
        
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <Result>
                    <result>2.017241</result>
                  </Result>
                  <Result>
                    <result>28.77586</result>
                  </Result>
                  <Result>
                    <result>-0.6</result>
                  </Result>
                  <Result>
                    <result>error!</result>
                  </Result>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }
}

class XMLNonAPIWriterTests {
    private String outputPath = "TestOutput.xml";

    @Test
    void writeString() throws IOException {
        String str = "AbRAcaDabrA";
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLNonAPIWriter XW = new XMLNonAPIWriter(outputPath);
        XW.WriteString(str);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <Data>AbRAcaDabrA</Data>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeInteger() throws IOException {
        int i = 113;
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLNonAPIWriter XW = new XMLNonAPIWriter(outputPath);
        XW.WriteInteger(i);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <Data>113</Data>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeMathExpression() throws IOException {
        String expression = "x + y + t + p / r + g";
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLNonAPIWriter XW = new XMLNonAPIWriter(outputPath);
        XW.WriteMathExpression(mathExpression);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <MathExpression>
                    <expression>x + y + t + p / r + g</expression>
                    <variables>"x""y""t""g""p""r"</variables>
                    <types>"i""i""d""i""d""d"</types>
                    <integers>"(0,0)""(1,1)""(1,3)"</integers>
                    <doubles>"(1.0,2)""(0.057,4)""(-0.058,5)"</doubles>
                  </MathExpression>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeListOfMathExpressions() throws IOException {
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
        al.add("x = 6.0e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLNonAPIWriter XW = new XMLNonAPIWriter(outputPath);
        XW.WriteListOfMathExpressions(mel);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <MathExpression>
                    <expression>x + y + t + p / r + g</expression>
                    <variables>"x""y""t""g""p""r"</variables>
                    <types>"i""i""d""i""d""d"</types>
                    <integers>"(0,0)""(1,1)""(1,3)"</integers>
                    <doubles>"(1.0,2)""(0.057,4)""(-0.058,5)"</doubles>
                  </MathExpression>
                  <MathExpression>
                    <expression>x + y + t + p / r</expression>
                    <variables>"x""y""t""p""r"</variables>
                    <types>"i""i""i""d""d"</types>
                    <integers>"(6,0)""(1,1)""(40,2)"</integers>
                    <doubles>"(1.057,3)""(-0.058,4)"</doubles>
                  </MathExpression>
                  <MathExpression>
                    <expression>x * y</expression>
                    <variables>"x""x""y"</variables>
                    <types>"d""d""d"</types>
                    <integers>""</integers>
                    <doubles>"(6.0E-6,0)""(6.0E-6,1)""(-100000.0,2)"</doubles>
                  </MathExpression>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeResult() throws IOException {
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLNonAPIWriter XW = new XMLNonAPIWriter(outputPath);
        XW.WriteResult(new Result('e'));
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <Result>
                    <result>error!</result>
                  </Result>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeListOfResultsOfMathExpressions() throws IOException {
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
        al.add("x = 6.0e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        al = new ArrayList<>();
        al.add("x = 6e-6");
        mel.add(new MathExpression("x * y", al));
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLNonAPIWriter XW = new XMLNonAPIWriter(outputPath);
        XW.WriteListOfResultsOfMathExpressions(mel, 2);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <Content>
                  <Result>
                    <result>2.017241</result>
                  </Result>
                  <Result>
                    <result>28.77586</result>
                  </Result>
                  <Result>
                    <result>-0.6</result>
                  </Result>
                  <Result>
                    <result>error!</result>
                  </Result>
                </Content>
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }
}

class XMLAPI_NonAPIMatchingWritingTest {
    private String outputPath = "TestOutput.xml";

    @Test
    void Test() throws IOException {
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
        al.add("x = 6.0e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        XMLWriter XW = new XMLWriter(outputPath);
        XW.WriteListOfMathExpressions(mel);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result1 = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result1.append(line);
            result1.append("\n");
        }
        BFR.close();
        XMLNonAPIWriter XNAW = new XMLNonAPIWriter(outputPath);
        XNAW.WriteListOfMathExpressions(mel);
        BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result2 = new StringBuilder();
        while ((line = BFR.readLine()) != null) {
            result2.append(line);
            result2.append("\n");
        }
        BFR.close();
        assertEquals(result1.toString(), result2.toString());
        File output = new File(outputPath);
        if (output.exists()) {
            output.delete();
        }
    }
}

class JsonWriterTests {
    private String outputPath = "TestOutput.json";

    @Test
    void writeString() throws IOException {
        String str = "AbRAcaDabrA";
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonWriter XW = new JsonWriter(outputPath);
        XW.WriteString(str);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                "AbRAcaDabrA"
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeInteger() throws IOException {
        int i = 113;
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonWriter JW = new JsonWriter(outputPath);
        JW.WriteInteger(i);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                113
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeMathExpression() throws IOException {
        String expression = "x + y + t + p / r + g";
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonWriter JW = new JsonWriter(outputPath);
        JW.WriteMathExpression(mathExpression);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                {
                  "expression" : "x + y + t + p / r + g",
                  "variables" : [ "x", "y", "t", "g", "p", "r" ],
                  "types" : [ "i", "i", "d", "i", "d", "d" ],
                  "integers" : [ {
                    "0" : 0
                  }, {
                    "1" : 1
                  }, {
                    "1" : 3
                  } ],
                  "doubles" : [ {
                    "1.0" : 2
                  }, {
                    "0.057" : 4
                  }, {
                    "-0.058" : 5
                  } ]
                }
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeListOfMathExpressions() throws IOException {
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
        al.add("x = 6.0e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonWriter JW = new JsonWriter(outputPath);
        JW.WriteListOfMathExpressions(mel);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                [ {
                  "expression" : "x + y + t + p / r + g",
                  "variables" : [ "x", "y", "t", "g", "p", "r" ],
                  "types" : [ "i", "i", "d", "i", "d", "d" ],
                  "integers" : [ {
                    "0" : 0
                  }, {
                    "1" : 1
                  }, {
                    "1" : 3
                  } ],
                  "doubles" : [ {
                    "1.0" : 2
                  }, {
                    "0.057" : 4
                  }, {
                    "-0.058" : 5
                  } ]
                }, {
                  "expression" : "x + y + t + p / r",
                  "variables" : [ "x", "y", "t", "p", "r" ],
                  "types" : [ "i", "i", "i", "d", "d" ],
                  "integers" : [ {
                    "6" : 0
                  }, {
                    "1" : 1
                  }, {
                    "40" : 2
                  } ],
                  "doubles" : [ {
                    "1.057" : 3
                  }, {
                    "-0.058" : 4
                  } ]
                }, {
                  "expression" : "x * y",
                  "variables" : [ "x", "x", "y" ],
                  "types" : [ "d", "d", "d" ],
                  "integers" : [ ],
                  "doubles" : [ {
                    "6.0E-6" : 0
                  }, {
                    "6.0E-6" : 1
                  }, {
                    "-100000.0" : 2
                  } ]
                } ]
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeResult() throws IOException {
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonWriter JW = new JsonWriter(outputPath);
        JW.WriteResult(new Result('e'));
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                {
                  "result" : "error!"
                }
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeListOfResultsOfMathExpressions() throws IOException {
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
        al.add("x = 6.0e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        al = new ArrayList<>();
        al.add("x = 6e-6");
        mel.add(new MathExpression("x * y", al));
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonWriter JW = new JsonWriter(outputPath);
        JW.WriteListOfResultsOfMathExpressions(mel, 2);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                [ {
                  "result" : "2.017241"
                }, {
                  "result" : "28.77586"
                }, {
                  "result" : "-0.6"
                }, {
                  "result" : "error!"
                } ]
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }
}

class JsonNonAPIWriterTests {
    private String outputPath = "TestOutput.json";

    @Test
    void writeString() throws IOException {
        String str = "AbRAcaDabrA";
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonNonAPIWriter JW = new JsonNonAPIWriter(outputPath);
        JW.WriteString(str);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                "AbRAcaDabrA"
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeInteger() throws IOException {
        int i = 113;
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonNonAPIWriter JW = new JsonNonAPIWriter(outputPath);
        JW.WriteInteger(i);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                113
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeMathExpression() throws IOException {
        String expression = "x + y + t + p / r + g";
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonNonAPIWriter JW = new JsonNonAPIWriter(outputPath);
        JW.WriteMathExpression(mathExpression);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                {
                  "expression" : "x + y + t + p / r + g",
                  "variables" : [ "x", "y", "t", "g", "p", "r" ],
                  "types" : [ "i", "i", "d", "i", "d", "d" ],
                  "integers" : [ {
                    "0" : 0
                  }, {
                    "1" : 1
                  }, {
                    "1" : 3
                  } ],
                  "doubles" : [ {
                    "1.0" : 2
                  }, {
                    "0.057" : 4
                  }, {
                    "-0.058" : 5
                  } ]
                }
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeListOfMathExpressions() throws IOException {
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
        al.add("x = 6.0e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonNonAPIWriter JW = new JsonNonAPIWriter(outputPath);
        JW.WriteListOfMathExpressions(mel);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                [ {
                  "expression" : "x + y + t + p / r + g",
                  "variables" : [ "x", "y", "t", "g", "p", "r" ],
                  "types" : [ "i", "i", "d", "i", "d", "d" ],
                  "integers" : [ {
                    "0" : 0
                  }, {
                    "1" : 1
                  }, {
                    "1" : 3
                  } ],
                  "doubles" : [ {
                    "1.0" : 2
                  }, {
                    "0.057" : 4
                  }, {
                    "-0.058" : 5
                  } ]
                }, {
                  "expression" : "x + y + t + p / r",
                  "variables" : [ "x", "y", "t", "p", "r" ],
                  "types" : [ "i", "i", "i", "d", "d" ],
                  "integers" : [ {
                    "6" : 0
                  }, {
                    "1" : 1
                  }, {
                    "40" : 2
                  } ],
                  "doubles" : [ {
                    "1.057" : 3
                  }, {
                    "-0.058" : 4
                  } ]
                }, {
                  "expression" : "x * y",
                  "variables" : [ "x", "x", "y" ],
                  "types" : [ "d", "d", "d" ],
                  "integers" : [ ],
                  "doubles" : [ {
                    "6.0E-6" : 0
                  }, {
                    "6.0E-6" : 1
                  }, {
                    "-100000.0" : 2
                  } ]
                } ]
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeResult() throws IOException {
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonNonAPIWriter JW = new JsonNonAPIWriter(outputPath);
        JW.WriteResult(new Result('e'));
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                {
                  "result" : "error!"
                }
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void writeListOfResultsOfMathExpressions() throws IOException {
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
        al.add("x = 6.0e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        al = new ArrayList<>();
        al.add("x = 6e-6");
        mel.add(new MathExpression("x * y", al));
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonNonAPIWriter JW = new JsonNonAPIWriter(outputPath);
        JW.WriteListOfResultsOfMathExpressions(mel, 2);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                [ {
                  "result" : "2.017241"
                }, {
                  "result" : "28.77586"
                }, {
                  "result" : "-0.6"
                }, {
                  "result" : "error!"
                } ]
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }
}

class JsonAPI_NonAPIMatchingWritingTest {
    private String outputPath = "TestOutput.json";

    @Test
    void Test() throws IOException {
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
        al.add("x = 6.0e-6");
        al.add("y = -1e5");
        mel.add(new MathExpression("x * y", al));
        JsonWriter JW = new JsonWriter(outputPath);
        JW.WriteListOfMathExpressions(mel);
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result1 = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result1.append(line);
            result1.append("\n");
        }
        BFR.close();
        JsonNonAPIWriter JNAW = new JsonNonAPIWriter(outputPath);
        JNAW.WriteListOfMathExpressions(mel);
        BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result2 = new StringBuilder();
        while ((line = BFR.readLine()) != null) {
            result2.append(line);
            result2.append("\n");
        }
        BFR.close();
        assertEquals(result1.toString(), result2.toString());
        File output = new File(outputPath);
        if (output.exists()) {
            output.delete();
        }
    }
}