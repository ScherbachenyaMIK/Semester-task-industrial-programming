import java.io.*;
import java.util.ArrayList;

public class _BufferedFileReader {
    private BufferedReader File_;
    _BufferedFileReader(_FileReader fileReader) throws FileNotFoundException {
        File_ = new BufferedReader(fileReader.getFile_());
    }
    _BufferedFileReader(InputStream fileReader, String charsetName) throws FileNotFoundException, UnsupportedEncodingException {  //not working
        File_ = new BufferedReader(new InputStreamReader(fileReader, charsetName));
    }
    public void CloseFile() throws IOException {
        File_.close();
    }
    public String ReadString() throws IOException {
        String str;
        str = File_.readLine();
        return str;
    }
    public int ReadInteger() throws IOException {
        String str;
        str = File_.readLine();
        return Integer.parseInt(str);
    }
    public MathExpression ReadMathExpression() throws IOException {
        String expression = ReadString();
        if (expression == null)
        {
            return null;
        }
        ArrayList<String> variables = new ArrayList<>();
        String variable;
        while((variable = ReadString()) != null && !variable.startsWith("Task"))
        {
            variables.add(variable);
        }
        return new MathExpression(expression, variables);
    }
    public ArrayList<MathExpression> ReadListOfMathExpressions() throws IOException {
        ArrayList<MathExpression> expressions = new ArrayList<>();
        MathExpression expression;
        String variable = ReadString();
        if (!variable.startsWith("Task")) {
            throw new IllegalArgumentException("The text file is damaged or does not match the format");
        }
        while ((expression = ReadMathExpression()) != null)
        {
            expressions.add(expression);
        }
        return expressions;
    }
}
