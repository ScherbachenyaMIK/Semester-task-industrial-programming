import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {
    private String inputPath = "TestInput.txt";

    @Test
    void readString() throws IOException {
        String str = "Hello world!";
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteString(str);
        JsonReader JR = new JsonReader(inputPath);
        String result = JR.ReadString();
        assertEquals(str, result);
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
            JsonReader FR = new JsonReader(inputPath);
            String result = FR.ReadString();
            fail();
        }
        catch (FileNotFoundException exception) {

        }
    }

    @Test
    void readInteger() throws IOException {
        int i = 113;
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteInteger(i);
        JsonReader JR = new JsonReader(inputPath);
        int result = JR.ReadInteger();
        assertEquals(i, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readIntegerFail() throws IOException {
        String str = "Hello world!";
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteString(str);
        JsonReader JR = new JsonReader(inputPath);
        try {
            int result = JR.ReadInteger();
            fail();
        }
        catch (IllegalArgumentException exception) {
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
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteMathExpression(mathExpression);
        JsonReader FR = new JsonReader(inputPath);
        MathExpression result = FR.ReadMathExpression();
        assertEquals(mathExpression, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readMathExpressionFail() throws IOException {
        String str = "Hello world!";
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteString(str);
        JsonReader JR = new JsonReader(inputPath);
        try {
            MathExpression result = JR.ReadMathExpression();
            fail();
        }
        catch (IllegalArgumentException exception) {
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
        JsonWriter FW = new JsonWriter(inputPath);
        FW.WriteListOfMathExpressions(mel);
        JsonReader JR = new JsonReader(inputPath);
        ArrayList<MathExpression> result = JR.ReadListOfMathExpressions();
        assertEquals(mel, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readListOfMathExpressionsFail() throws IOException {
        String str = "Hello world!";
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteString(str);
        JsonReader JR = new JsonReader(inputPath);
        try {
            ArrayList<MathExpression> result = JR.ReadListOfMathExpressions();
        }
        catch (IllegalArgumentException exception) {
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }
}

class JsonNonAPIReaderTest {
    private String inputPath = "TestInput.txt";

    @Test
    void readString() throws IOException {
        String str = "Hello world!";
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteString(str);
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        String result = JR.ReadString();
        JR.CloseJsonNonAPIReader();
        assertEquals(str, result);
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
            JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
            String result = JR.ReadString();
            fail();
        }
        catch (FileNotFoundException exception) {

        }
    }

    @Test
    void readInteger() throws IOException {
        int i = 113;
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteInteger(i);
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        int result = JR.ReadInteger();
        JR.CloseJsonNonAPIReader();
        assertEquals(i, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readIntegerFail() throws IOException {
        String str = "Hello world!";
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteString(str);
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        try {
            int result = JR.ReadInteger();
            fail();
        }
        catch (IllegalArgumentException exception) {
            JR.CloseJsonNonAPIReader();
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
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteMathExpression(mathExpression);
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        MathExpression result = JR.ReadMathExpression();
        JR.CloseJsonNonAPIReader();
        assertEquals(mathExpression, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readMathExpressionFail() throws IOException {
        String str = "Hello world!";
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteString(str);
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        try {
            MathExpression result = JR.ReadMathExpression();
            fail();
        }
        catch (IllegalArgumentException exception) {
            JR.CloseJsonNonAPIReader();
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
        JsonWriter FW = new JsonWriter(inputPath);
        FW.WriteListOfMathExpressions(mel);
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        ArrayList<MathExpression> result = JR.ReadListOfMathExpressions();
        JR.CloseJsonNonAPIReader();
        assertEquals(mel, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readListOfMathExpressionsFail() throws IOException {
        String str = "Hello world!";
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteString(str);
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        try {
            ArrayList<MathExpression> result = JR.ReadListOfMathExpressions();
        }
        catch (IllegalArgumentException exception) {
            JR.CloseJsonNonAPIReader();
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }
}

class JsonAPI_NonAPIMatchingTest {
    private String inputPath = "TestInput.txt";

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
        JsonWriter FW = new JsonWriter(inputPath);
        FW.WriteListOfMathExpressions(mel);
        JsonReader JR = new JsonReader(inputPath);
        JsonNonAPIReader JNAR = new JsonNonAPIReader(inputPath);
        ArrayList<MathExpression> result1 = JR.ReadListOfMathExpressions();
        ArrayList<MathExpression> result2 = JNAR.ReadListOfMathExpressions();
        JNAR.CloseJsonNonAPIReader();
        assertEquals(result1, result2);
        assertEquals(mel, result1);
        assertEquals(mel, result2);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }
}