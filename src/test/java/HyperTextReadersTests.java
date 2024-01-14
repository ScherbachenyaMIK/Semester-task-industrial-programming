import main.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {
    private String inputPath = "TestInput.json";

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
            JsonReader JR = new JsonReader(inputPath);
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
            fail();
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
    private String inputPath = "TestInput.json";

    @Test
    void readTextString() throws IOException {
        String str = "Hello world!";
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteString(str);
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        String result = JR.ReadTextString();
        JR.CloseReader();
        assertEquals(str, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readTextStringFail() throws IOException {
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
        try {
            JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
            String result = JR.ReadTextString();
            fail();
        }
        catch (FileNotFoundException exception) {

        }
    }

    @Test
    void readString() throws IOException {
        String str = "Hello world!";
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteString(str);
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        String result = JR.ReadString();
        JR.CloseReader();
        assertEquals(str, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readStringFail() throws IOException {
        _FileWriter FW = new _FileWriter(inputPath);
        FW.CloseFile();
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        try {
            String result = JR.ReadString();
            fail();
        }
        catch (IllegalArgumentException exception) {
            JR.CloseReader();
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }

    @Test
    void readInteger() throws IOException {
        int i = 113;
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteInteger(i);
        JsonNonAPIReader JR = new JsonNonAPIReader(inputPath);
        int result = JR.ReadInteger();
        JR.CloseReader();
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
            JR.CloseReader();
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
        JR.CloseReader();
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
            JR.CloseReader();
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
        JR.CloseReader();
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
            fail();
        }
        catch (IllegalArgumentException exception) {
            JR.CloseReader();
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }
}

class JsonAPI_NonAPIMatchingTest {
    private String inputPath = "TestInput.json";

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
        JsonWriter JW = new JsonWriter(inputPath);
        JW.WriteListOfMathExpressions(mel);
        JsonReader JR = new JsonReader(inputPath);
        JsonNonAPIReader JNAR = new JsonNonAPIReader(inputPath);
        ArrayList<MathExpression> result1 = JR.ReadListOfMathExpressions();
        ArrayList<MathExpression> result2 = JNAR.ReadListOfMathExpressions();
        JNAR.CloseReader();
        assertEquals(result1, result2);
        assertEquals(mel, result1);
        assertEquals(mel, result2);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }
}

class XMLReaderTest {
    private String inputPath = "TestInput.xml";

    @Test
    void readString() throws IOException {
        String str = "Hello world!";
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteString(str);
        XMLReader XR = new XMLReader(inputPath);
        String result = XR.ReadString();
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
            XMLReader XR = new XMLReader(inputPath);
            String result = XR.ReadString();
            fail();
        }
        catch (FileNotFoundException exception) {

        }
    }

    @Test
    void readInteger() throws IOException {
        int i = 113;
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteInteger(i);
        XMLReader XR = new XMLReader(inputPath);
        int result = XR.ReadInteger();
        assertEquals(i, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readIntegerFail() throws IOException {
        String str = "Hello world!";
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteString(str);
        XMLReader XR = new XMLReader(inputPath);
        try {
            int result = XR.ReadInteger();
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
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteMathExpression(mathExpression);
        XMLReader XR = new XMLReader(inputPath);
        MathExpression result = XR.ReadMathExpression();
        assertEquals(mathExpression, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readMathExpressionFail() throws IOException {
        String str = "Hello world!";
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteString(str);
        XMLReader XR = new XMLReader(inputPath);
        try {
            MathExpression result = XR.ReadMathExpression();
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
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteListOfMathExpressions(mel);
        XMLReader XR = new XMLReader(inputPath);
        ArrayList<MathExpression> result = XR.ReadListOfMathExpressions();
        assertEquals(mel, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readListOfMathExpressionsFail() throws IOException {
        String str = "Hello world!";
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteString(str);
        XMLReader XR = new XMLReader(inputPath);
        try {
            ArrayList<MathExpression> result = XR.ReadListOfMathExpressions();
            fail();
        }
        catch (IllegalArgumentException exception) {
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }
}

class XMLNonAPIReaderTest {
    private String inputPath = "TestInput.xml";

    @Test
    void readString() throws IOException {
        String str = "Hello world!";
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteString(str);
        XMLNonAPIReader XR = new XMLNonAPIReader(inputPath);
        String result = XR.ReadString();
        XR.CloseReader();
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
            XMLNonAPIReader XR = new XMLNonAPIReader(inputPath);
            String result = XR.ReadString();
            fail();
        }
        catch (FileNotFoundException exception) {

        }
    }

    @Test
    void readInteger() throws IOException {
        int i = 113;
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteInteger(i);
        XMLNonAPIReader XR = new XMLNonAPIReader(inputPath);
        int result = XR.ReadInteger();
        XR.CloseReader();
        assertEquals(i, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readIntegerFail() throws IOException {
        String str = "Hello world!";
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteString(str);
        XMLNonAPIReader XR = new XMLNonAPIReader(inputPath);
        try {
            int result = XR.ReadInteger();
            fail();
        }
        catch (IllegalArgumentException exception) {
            XR.CloseReader();
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
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteMathExpression(mathExpression);
        XMLNonAPIReader XR = new XMLNonAPIReader(inputPath);
        MathExpression result = XR.ReadMathExpression();
        XR.CloseReader();
        assertEquals(mathExpression, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readMathExpressionFail() throws IOException {
        String str = "Hello world!";
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteString(str);
        XMLNonAPIReader XR = new XMLNonAPIReader(inputPath);
        try {
            MathExpression result = XR.ReadMathExpression();
            fail();
        }
        catch (IllegalArgumentException exception) {
            XR.CloseReader();
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
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteListOfMathExpressions(mel);
        XMLNonAPIReader XR = new XMLNonAPIReader(inputPath);
        ArrayList<MathExpression> result = XR.ReadListOfMathExpressions();
        XR.CloseReader();
        assertEquals(mel, result);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }

    @Test
    void readListOfMathExpressionsFail() throws IOException {
        String str = "Hello world!";
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteString(str);
        XMLNonAPIReader XR = new XMLNonAPIReader(inputPath);
        try {
            ArrayList<MathExpression> result = XR.ReadListOfMathExpressions();
            fail();
        }
        catch (IllegalArgumentException exception) {
            XR.CloseReader();
            File input = new File(inputPath);
            if (input.exists()) {
                input.delete();
            }
        }
    }
}

class XMLAPI_NonAPIMatchingTest {
    private String inputPath = "TestInput.xml";

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
        XMLWriter XW = new XMLWriter(inputPath);
        XW.WriteListOfMathExpressions(mel);
        XMLReader XR = new XMLReader(inputPath);
        XMLNonAPIReader XNAR = new XMLNonAPIReader(inputPath);
        ArrayList<MathExpression> result1 = XR.ReadListOfMathExpressions();
        ArrayList<MathExpression> result2 = XNAR.ReadListOfMathExpressions();
        XNAR.CloseReader();
        assertEquals(result1, result2);
        assertEquals(mel, result1);
        assertEquals(mel, result2);
        File input = new File(inputPath);
        if (input.exists()) {
            input.delete();
        }
    }
}