import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterTest {
    private String outputPath = "TestOutput.txt";

    @Test
    void writeString() throws IOException {
        String str = "AbRAcaDabrA";
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        _FileWriter FW = new _FileWriter(outputPath);
        FW.WriteString(str);
        FW.CloseFile();
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        String result = BFR.readLine();
        assertEquals(str, result);
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
        _FileWriter FW = new _FileWriter(outputPath);
        FW.WriteInteger(i);
        FW.CloseFile();
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        int result = Integer.parseInt(BFR.readLine().trim());
        assertEquals(i, result);
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
        _FileWriter FW = new _FileWriter(outputPath);
        FW.WriteMathExpression(mathExpression);
        FW.CloseFile();
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                Task 1:
                x + y + t + p / r + g
                x = 0
                y = 1
                t = 1.0
                g = 1
                p = 0.057
                r = -0.058
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
        _FileWriter FW = new _FileWriter(outputPath);
        FW.WriteListOfMathExpressions(mel);
        FW.CloseFile();
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                Task 1:
                x + y + t + p / r + g
                x = 0
                y = 1
                t = 1.0
                g = 1
                p = 0.057
                r = -0.058
                Task 2:
                x + y + t + p / r
                x = 6
                y = 1
                t = 40
                p = 1.057
                r = -0.058
                Task 3:
                x * y
                x = 6.0E-6
                x = 6.0E-6
                y = -100000.0
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
        _FileWriter FW = new _FileWriter(outputPath);
        FW.WriteListOfResultsOfMathExpressions(mel, 2);
        FW.CloseFile();
        BufferedReader BFR = new BufferedReader(new FileReader(outputPath));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = BFR.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        assertEquals("""
                Task 1:
                2.017241
                Task 2:
                28.77586
                Task 3:
                -0.6
                Task 4:
                Error while computing expression!
                Original expression:
                Task 4:
                x * y
                x = 6.0E-6
                """, result.toString());
        BFR.close();
        if (file.exists()) {
            file.delete();
        }
    }
}