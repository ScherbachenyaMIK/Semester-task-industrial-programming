import main.MathExpression;
import main._BufferedFileReader;
import main._FileReader;
import main._FileWriter;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class FileReaderTest {
    private String inputPath = "TestInput.txt";

    @Test
    void readString() throws IOException {
        String str = "Hello world!";
        Writer BW = new FileWriter(inputPath);
        BW.write(str);
        BW.close();
        _FileReader FR = new _FileReader(inputPath);
        String result = FR.ReadString();
        assertEquals(str, result);
        FR.CloseFile();
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readStringFail() throws IOException {
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
        try {
        _FileReader FR = new _FileReader(inputPath);
            String result = FR.ReadString();
            FR.CloseFile();
            fail();
        }
        catch (FileNotFoundException exception) {

        }
    }

    @Test
    void readInteger() throws IOException {
        int i = 113;
        BufferedWriter BW = new BufferedWriter(new FileWriter(inputPath));
        BW.write(Integer.toString(i));
        BW.close();
        _FileReader FR = new _FileReader(inputPath);
        int result = FR.ReadInteger();
        assertEquals(i, result);
        FR.CloseFile();
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readIntegerFail() throws IOException {
        String str = "Hello world!";
        BufferedWriter BW = new BufferedWriter(new FileWriter(inputPath));
        BW.write(str);
        BW.close();
        _FileReader FR = new _FileReader(inputPath);
        try {
            int result = FR.ReadInteger();
            fail();
        }
        catch (NumberFormatException exception) {
            FR.CloseFile();
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }

    @Test
    void readMathExpression() throws IOException {
        String expression = "x + y + t + p / r + g";
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        _FileWriter FW = new _FileWriter(inputPath);
        FW.WriteMathExpression(mathExpression);
        FW.CloseFile();
        _FileReader FR = new _FileReader(inputPath);
        MathExpression result = FR.ReadMathExpression();
        assertEquals(mathExpression, result);
        FR.CloseFile();
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readMathExpressionFail() throws IOException {
        String str = "Hello world!";
        BufferedWriter BW = new BufferedWriter(new FileWriter(inputPath));
        BW.write(str);
        BW.close();
        _FileReader FR = new _FileReader(inputPath);
        try {
            MathExpression result = FR.ReadMathExpression();
            fail();
        }
        catch (IllegalArgumentException exception) {
            FR.CloseFile();
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }

    @Test
    void readListOfMathExpressions() throws IOException {
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
        _FileWriter FW = new _FileWriter(inputPath);
        FW.WriteListOfMathExpressions(mel);
        FW.CloseFile();
        _FileReader FR = new _FileReader(inputPath);
        ArrayList<MathExpression> result = FR.ReadListOfMathExpressions();
        FR.CloseFile();
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readListOfMathExpressionsFail() throws IOException {
        String str = "Hello world!";
        _FileWriter FW = new _FileWriter(inputPath);
        FW.WriteString(str);
        FW.CloseFile();
        _FileReader FR = new _FileReader(inputPath);
        try {
            ArrayList<MathExpression> result = FR.ReadListOfMathExpressions();
        }
        catch (IllegalArgumentException exception) {
            FR.CloseFile();
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }
}

class BufferedFileReaderTest {
    private String inputPath = "TestInput.txt";

    @Test
    void readString() throws IOException {
        String str = "Hello world!";
        BufferedWriter BW = new BufferedWriter(new FileWriter(inputPath));
        BW.write(str);
        BW.close();
        _BufferedFileReader BFR = new _BufferedFileReader(new _FileReader(inputPath));
        String result = BFR.ReadString();
        assertEquals(str, result);
        BFR.CloseFile();
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readStringFail() throws IOException {
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
        try {
            _BufferedFileReader BFR = new _BufferedFileReader(new _FileReader(inputPath));
            String result = BFR.ReadString();
            BFR.CloseFile();
            fail();
        }
        catch (FileNotFoundException exception) {

        }
    }

    @Test
    void readInteger() throws IOException {
        int i = 113;
        BufferedWriter BW = new BufferedWriter(new FileWriter(inputPath));
        BW.write(Integer.toString(i));
        BW.close();
        _BufferedFileReader BFR = new _BufferedFileReader(new _FileReader(inputPath));
        int result = BFR.ReadInteger();
        assertEquals(i, result);
        BFR.CloseFile();
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readIntegerFail() throws IOException {
        String str = "Hello world!";
        BufferedWriter BW = new BufferedWriter(new FileWriter(inputPath));
        BW.write(str);
        BW.close();
        _BufferedFileReader BFR = new _BufferedFileReader(new _FileReader(inputPath));
        try {
            int result = BFR.ReadInteger();
            fail();
        }
        catch (NumberFormatException exception) {
            BFR.CloseFile();
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }

    @Test
    void readMathExpression() throws IOException {
        String expression = "x + y + t + p / r + g";
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("x = 0");
        arrayList.add("y = 1");
        arrayList.add("t = 1.0");
        arrayList.add("g = 1");
        arrayList.add("p = 0.057");
        arrayList.add("r = -0.058");
        MathExpression mathExpression = new MathExpression(expression, arrayList);
        _FileWriter FW = new _FileWriter(inputPath);
        FW.WriteMathExpression(mathExpression);
        FW.CloseFile();
        _BufferedFileReader BFR = new _BufferedFileReader(new _FileReader(inputPath));
        MathExpression result = BFR.ReadMathExpression();
        assertEquals(mathExpression, result);
        BFR.CloseFile();
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readMathExpressionFail() throws IOException {
        String str = "Hello world!";
        BufferedWriter BW = new BufferedWriter(new FileWriter(inputPath));
        BW.write(str);
        BW.close();
        _BufferedFileReader BFR = new _BufferedFileReader(new _FileReader(inputPath));
        try {
            MathExpression result = BFR.ReadMathExpression();
            fail();
        }
        catch (IllegalArgumentException exception) {
            BFR.CloseFile();
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }

    @Test
    void readListOfMathExpressions() throws IOException {
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
        _FileWriter FW = new _FileWriter(inputPath);
        FW.WriteListOfMathExpressions(mel);
        FW.CloseFile();
        _BufferedFileReader BFR = new _BufferedFileReader(new _FileReader(inputPath));
        ArrayList<MathExpression> result = BFR.ReadListOfMathExpressions();
        assertEquals(mel, result);
        BFR.CloseFile();
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readListOfMathExpressionsFail() throws IOException {
        String str = "Hello world!";
        _FileWriter FW = new _FileWriter(inputPath);
        FW.WriteString(str);
        FW.CloseFile();
        _BufferedFileReader BFR = new _BufferedFileReader(new _FileReader(inputPath));
        try {
            ArrayList<MathExpression> result = BFR.ReadListOfMathExpressions();
        }
        catch (IllegalArgumentException exception) {
            BFR.CloseFile();
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }
}