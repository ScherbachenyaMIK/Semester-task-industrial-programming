import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.udojava.evalex.Expression;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class XMLWriter {
    private String filename;
    private File File_;
    private Document document;
    private Element root;

    public XMLWriter(String filename_) {
        filename = filename_;
    }
    private void OpenXMLWriter() throws IOException {
        File_ = new File(filename);
        if (!File_.exists()) {
            _FileWriter FW = new _FileWriter(filename);
            FW.CloseFile();
        }
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException exception) {
            throw new RuntimeException(exception);
        }
        document = documentBuilder.newDocument();
        root = document.createElement("Content");
        document.appendChild(root);
    }
    private void CloseXMLWriter() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(File_);
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
    public void WriteString(String data) throws IOException {
        OpenXMLWriter();
        Element main = document.createElement("Data");
        main.appendChild(document.createTextNode(data));
        root.appendChild(main);
        CloseXMLWriter();
    }

    public void WriteInteger(int data) throws IOException {
        WriteString(String.valueOf(data));
    }

    public void WriteMathExpression(MathExpression data) throws IOException {
        OpenXMLWriter();
        Element main = document.createElement("MathExpression");
        root.appendChild(main);

        Element expressionElement = document.createElement("expression");
        expressionElement.appendChild(document.createTextNode(data.getExpression()));
        main.appendChild(expressionElement);

        Element variablesElement = document.createElement("variables");
        if (data.getVariables().isEmpty()) {
            variablesElement.appendChild(document.createTextNode("\"\""));
        }
        else {
            for (char var : data.getVariables()) {
                variablesElement.appendChild(document.createTextNode("\"" + var + "\""));
            }
        }
        main.appendChild(variablesElement);

        Element typesElement = document.createElement("types");
        if (data.getTypes().isEmpty()) {
            typesElement.appendChild(document.createTextNode("\"\""));
        }
        else {
            for (char var : data.getTypes()) {
                typesElement.appendChild(document.createTextNode("\"" + var + "\""));
            }
        }
        main.appendChild(typesElement);

        Element integersElement = document.createElement("integers");
        if (data.getIntegers().isEmpty()) {
            integersElement.appendChild(document.createTextNode("\"\""));
        }
        else {
            for (ImmutablePair<Integer, Integer> var : data.getIntegers()) {
                integersElement.appendChild(document.createTextNode("\"" + var + "\""));
            }
        }
        main.appendChild(integersElement);

        Element doublesElement = document.createElement("doubles");
        if (data.getDoubles().isEmpty()) {
            doublesElement.appendChild(document.createTextNode("\"\""));
        }
        else {
            for (ImmutablePair<Double, Integer> var : data.getDoubles()) {
                doublesElement.appendChild(document.createTextNode("\"" + var + "\""));
            }
        }
        main.appendChild(doublesElement);
        CloseXMLWriter();
    }
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        OpenXMLWriter();
        for(MathExpression data : expressions)
        {
            Element main = document.createElement("MathExpression");
            root.appendChild(main);

            Element expressionElement = document.createElement("expression");
            expressionElement.appendChild(document.createTextNode(data.getExpression()));
            main.appendChild(expressionElement);

            Element variablesElement = document.createElement("variables");
            if (data.getVariables().isEmpty()) {
                variablesElement.appendChild(document.createTextNode("\"\""));
            }
            else {
                for (char var : data.getVariables()) {
                    variablesElement.appendChild(document.createTextNode("\"" + var + "\""));
                }
            }
            main.appendChild(variablesElement);

            Element typesElement = document.createElement("types");
            if (data.getTypes().isEmpty()) {
                typesElement.appendChild(document.createTextNode("\"\""));
            }
            else {
                for (char var : data.getTypes()) {
                    typesElement.appendChild(document.createTextNode("\"" + var + "\""));
                }
            }
            main.appendChild(typesElement);

            Element integersElement = document.createElement("integers");
            if (data.getIntegers().isEmpty()) {
                integersElement.appendChild(document.createTextNode("\"\""));
            }
            else {
                for (ImmutablePair<Integer, Integer> var : data.getIntegers()) {
                    integersElement.appendChild(document.createTextNode("\"" + var + "\""));
                }
            }
            main.appendChild(integersElement);

            Element doublesElement = document.createElement("doubles");
            if (data.getDoubles().isEmpty()) {
                doublesElement.appendChild(document.createTextNode("\"\""));
            }
            else {
                for (ImmutablePair<Double, Integer> var : data.getDoubles()) {
                    doublesElement.appendChild(document.createTextNode("\"" + var + "\""));
                }
            }
            main.appendChild(doublesElement);
        }
        CloseXMLWriter();
    }
    public void WriteResult(Result result) throws IOException {
        OpenXMLWriter();
        Element main = document.createElement("Result");
        root.appendChild(main);

        Element resultElement = document.createElement("result");
        resultElement.appendChild(document.createTextNode(result.getResult()));
        main.appendChild(resultElement);
        CloseXMLWriter();
    }
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        OpenXMLWriter();
        ArrayList<Result> results = new ArrayList<>();
        for (MathExpression expression : expressions) {
            try {
                results.add(new Result(expression.Result(type)));
            } catch (IOException | Expression.ExpressionException | IllegalArgumentException exception) {
                results.add(new Result('e'));
            }
        }
        for (Result result : results) {
            Element main = document.createElement("Result");
            root.appendChild(main);

            Element resultElement = document.createElement("result");
            resultElement.appendChild(document.createTextNode(result.getResult()));
            main.appendChild(resultElement);
            CloseXMLWriter();
        }
        CloseXMLWriter();
    }
}

class XMLNonAPIWriter {
    private String filename;
    private BufferedWriter writer;

    XMLNonAPIWriter(String filename_) throws IOException {
        filename = filename_;
    }
    private void CloseXMLNonAPIWriter() throws IOException {
        writer.write("</Content>\n");
        writer.close();
    }
    private void OpenXMLNonAPIWriter() throws IOException {
        File File_ = new File(filename);
        writer = new BufferedWriter(new FileWriter(File_));
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Content>\n");
    }
    public void WriteString(String str) throws IOException {
        OpenXMLNonAPIWriter();
        writer.write("  <Data>" + str + "</Data>\n");
        CloseXMLNonAPIWriter();
    }
    public void WriteInteger(int i) throws IOException {
        OpenXMLNonAPIWriter();
        writer.write("  <Data>" + i + "</Data>\n");
        CloseXMLNonAPIWriter();
    }
    public void WriteMathExpression(MathExpression expression) throws IOException {
        OpenXMLNonAPIWriter();
        StringBuilder content = new StringBuilder();
        content.append("  <MathExpression>\n")
                .append("    <expression>").append(expression.getExpression()).append("</expression>\n")
                .append("    <variables>");
        String str = String.join("\"\"", expression.getVariables().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</variables>\n").append("    <types>");
        str = String.join("\"\"", expression.getTypes().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</types>\n").append("    <integers>");
        str = String.join("\"\"", expression.getIntegers().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</integers>\n").append("    <doubles>");
        str = String.join("\"\"", expression.getDoubles().toString().split(", "));
        str = str.replaceAll("\\[", "\"");
        str = str.replaceAll("]", "\"");
        content.append(str).append("</doubles>\n")
                .append("  </MathExpression>\n");
        writer.write(content.toString());
        CloseXMLNonAPIWriter();
    }
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        OpenXMLNonAPIWriter();
        for(MathExpression expression : expressions)
        {
            StringBuilder content = new StringBuilder();
            content.append("  <MathExpression>\n")
                    .append("    <expression>").append(expression.getExpression()).append("</expression>\n")
                    .append("    <variables>");
            String str = String.join("\"\"", expression.getVariables().toString().split(", "));
            str = str.replaceAll("\\[", "\"");
            str = str.replaceAll("]", "\"");
            content.append(str).append("</variables>\n").append("    <types>");
            str = String.join("\"\"", expression.getTypes().toString().split(", "));
            str = str.replaceAll("\\[", "\"");
            str = str.replaceAll("]", "\"");
            content.append(str).append("</types>\n").append("    <integers>");
            str = String.join("\"\"", expression.getIntegers().toString().split(", "));
            str = str.replaceAll("\\[", "\"");
            str = str.replaceAll("]", "\"");
            content.append(str).append("</integers>\n").append("    <doubles>");
            str = String.join("\"\"", expression.getDoubles().toString().split(", "));
            str = str.replaceAll("\\[", "\"");
            str = str.replaceAll("]", "\"");
            content.append(str).append("</doubles>\n")
                    .append("  </MathExpression>\n");
            writer.write(content.toString());
        }
        CloseXMLNonAPIWriter();
    }
    public void WriteResult(Result result) throws IOException {
        OpenXMLNonAPIWriter();
        StringBuilder content = new StringBuilder();
        content.append("  <Result>\n")
                .append("    <result>").append(result.getResult()).append("</result>\n")
                .append("  </Result>\n");
        writer.write(content.toString());
        CloseXMLNonAPIWriter();
    }
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        ArrayList<Result> results = new ArrayList<>();
        for (MathExpression expression : expressions) {
            try {
                results.add(new Result(expression.Result(type)));
            } catch (IOException | Expression.ExpressionException | IllegalArgumentException exception) {
                results.add(new Result('e'));
            }
        }
        OpenXMLNonAPIWriter();
        for (Result result : results) {
            StringBuilder content = new StringBuilder();
            content.append("  <Result>\n")
                    .append("    <result>").append(result.getResult()).append("</result>\n")
                    .append("  </Result>\n");
            writer.write(content.toString());
        }
        CloseXMLNonAPIWriter();
    }
}
