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
import java.util.ArrayList;

public class XMLWriter implements APIHyperTextWriter {
    private String filename;
    private File file;
    private Document document;
    private Element root;

    // Constructor to set the filename
    public XMLWriter(String filename) {
        this.filename = filename;
    }

    // Initialize XML writer
    private void openXMLWriter() throws IOException {
        file = new File(filename);

        // Create file if it doesn't exist
        if (!file.exists()) {
            _FileWriter fileWriter = new _FileWriter(filename);
            fileWriter.CloseFile();
        }

        // Create DocumentBuilder and initialize document structure
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

    // Close the XML writer and save the document to the file
    private void closeWriter() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty("omit-xml-declaration", "no");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    // Write a string to the XML file
    public void WriteString(String data) throws IOException {
        openXMLWriter();
        Element main = document.createElement("Data");
        main.appendChild(document.createTextNode(data));
        root.appendChild(main);
        closeWriter();
    }

    // Write an integer to the XML file
    public void WriteInteger(int data) throws IOException {
        WriteString(String.valueOf(data));
    }

    // Write a MathExpression to the XML file
    public void WriteMathExpression(MathExpression data) throws IOException {
        openXMLWriter();
        Element main = document.createElement("MathExpression");
        root.appendChild(main);

        // Write expression element
        Element expressionElement = document.createElement("expression");
        expressionElement.appendChild(document.createTextNode(data.getExpression()));
        main.appendChild(expressionElement);

        // Write variables element
        Element variablesElement = document.createElement("variables");
        if (data.getVariables().isEmpty()) {
            variablesElement.appendChild(document.createTextNode("\"\""));
        } else {
            for (char var : data.getVariables()) {
                variablesElement.appendChild(document.createTextNode("\"" + var + "\""));
            }
        }
        main.appendChild(variablesElement);

        // Write types element
        Element typesElement = document.createElement("types");
        if (data.getTypes().isEmpty()) {
            typesElement.appendChild(document.createTextNode("\"\""));
        } else {
            for (char var : data.getTypes()) {
                typesElement.appendChild(document.createTextNode("\"" + var + "\""));
            }
        }
        main.appendChild(typesElement);

        // Write integers element
        Element integersElement = document.createElement("integers");
        if (data.getIntegers().isEmpty()) {
            integersElement.appendChild(document.createTextNode("\"\""));
        } else {
            for (ImmutablePair<Integer, Integer> var : data.getIntegers()) {
                integersElement.appendChild(document.createTextNode("\"" + var + "\""));
            }
        }
        main.appendChild(integersElement);

        // Write doubles element
        Element doublesElement = document.createElement("doubles");
        if (data.getDoubles().isEmpty()) {
            doublesElement.appendChild(document.createTextNode("\"\""));
        } else {
            for (ImmutablePair<Double, Integer> var : data.getDoubles()) {
                doublesElement.appendChild(document.createTextNode("\"" + var + "\""));
            }
        }
        main.appendChild(doublesElement);

        closeWriter();
    }

    // Write a list of MathExpressions to the XML file
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        openXMLWriter();
        for (MathExpression data : expressions) {
            Element main = document.createElement("MathExpression");
            root.appendChild(main);

            // Write expression element
            Element expressionElement = document.createElement("expression");
            expressionElement.appendChild(document.createTextNode(data.getExpression()));
            main.appendChild(expressionElement);

            // Write variables element
            Element variablesElement = document.createElement("variables");
            if (data.getVariables().isEmpty()) {
                variablesElement.appendChild(document.createTextNode("\"\""));
            } else {
                for (char var : data.getVariables()) {
                    variablesElement.appendChild(document.createTextNode("\"" + var + "\""));
                }
            }
            main.appendChild(variablesElement);

            // Write types element
            Element typesElement = document.createElement("types");
            if (data.getTypes().isEmpty()) {
                typesElement.appendChild(document.createTextNode("\"\""));
            } else {
                for (char var : data.getTypes()) {
                    typesElement.appendChild(document.createTextNode("\"" + var + "\""));
                }
            }
            main.appendChild(typesElement);

            // Write integers element
            Element integersElement = document.createElement("integers");
            if (data.getIntegers().isEmpty()) {
                integersElement.appendChild(document.createTextNode("\"\""));
            } else {
                for (ImmutablePair<Integer, Integer> var : data.getIntegers()) {
                    integersElement.appendChild(document.createTextNode("\"" + var + "\""));
                }
            }
            main.appendChild(integersElement);

            // Write doubles element
            Element doublesElement = document.createElement("doubles");
            if (data.getDoubles().isEmpty()) {
                doublesElement.appendChild(document.createTextNode("\"\""));
            } else {
                for (ImmutablePair<Double, Integer> var : data.getDoubles()) {
                    doublesElement.appendChild(document.createTextNode("\"" + var + "\""));
                }
            }
            main.appendChild(doublesElement);
        }
        closeWriter();
    }

    // Write a Result to the XML file
    public void WriteResult(Result result) throws IOException {
        openXMLWriter();
        Element main = document.createElement("Result");
        root.appendChild(main);

        // Write result element
        Element resultElement = document.createElement("result");
        resultElement.appendChild(document.createTextNode(result.getResult()));
        main.appendChild(resultElement);

        closeWriter();
    }

    // Write a list of Results of MathExpressions to the XML file
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        openXMLWriter();
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

            // Write result element
            Element resultElement = document.createElement("result");
            resultElement.appendChild(document.createTextNode(result.getResult()));
            main.appendChild(resultElement);
            closeWriter();
        }
        closeWriter();
    }
}

class XMLNonAPIWriter implements NonAPIHyperTextWriter {
    private String filename;
    private BufferedWriter writer;

    // Constructor to set the filename
    XMLNonAPIWriter(String filename) throws IOException {
        this.filename = filename;
    }

    // Close the XML writer by writing the closing tag and closing the file
    public void CloseWriter() throws IOException {
        writer.write("</Content>\n");
        writer.close();
    }

    // Open the XML writer by creating the file and writing the opening tags
    public void OpenWriter() throws IOException {
        File File_ = new File(filename);
        writer = new BufferedWriter(new FileWriter(File_));
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Content>\n");
    }

    // Write a string to the XML file
    public void WriteString(String str) throws IOException {
        OpenWriter();
        writer.write("  <Data>" + str + "</Data>\n");
        CloseWriter();
    }

    // Write an integer to the XML file
    public void WriteInteger(int i) throws IOException {
        OpenWriter();
        writer.write("  <Data>" + i + "</Data>\n");
        CloseWriter();
    }

    // Write a MathExpression to the XML file
    public void WriteMathExpression(MathExpression expression) throws IOException {
        OpenWriter();
        StringBuilder content = new StringBuilder();
        content.append("  <MathExpression>\n")
                .append("    <expression>").append(expression.getExpression()).append("</expression>\n")
                .append("    <variables>");

        // Format and write variables
        String str = String.join("\"\"", expression.getVariables().toString().split(", "));
        str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
        content.append(str).append("</variables>\n").append("    <types>");

        // Format and write types
        str = String.join("\"\"", expression.getTypes().toString().split(", "));
        str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
        content.append(str).append("</types>\n").append("    <integers>");

        // Format and write integers
        str = String.join("\"\"", expression.getIntegers().toString().split(", "));
        str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
        content.append(str).append("</integers>\n").append("    <doubles>");

        // Format and write doubles
        str = String.join("\"\"", expression.getDoubles().toString().split(", "));
        str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
        content.append(str).append("</doubles>\n")
                .append("  </MathExpression>\n");
        writer.write(content.toString());
        CloseWriter();
    }

    // Write a list of MathExpressions to the XML file
    public void WriteListOfMathExpressions(ArrayList<MathExpression> expressions) throws IOException {
        OpenWriter();
        for (MathExpression expression : expressions) {
            StringBuilder content = new StringBuilder();
            content.append("  <MathExpression>\n")
                    .append("    <expression>").append(expression.getExpression()).append("</expression>\n")
                    .append("    <variables>");

            // Format and write variables
            String str = String.join("\"\"", expression.getVariables().toString().split(", "));
            str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
            content.append(str).append("</variables>\n").append("    <types>");

            // Format and write types
            str = String.join("\"\"", expression.getTypes().toString().split(", "));
            str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
            content.append(str).append("</types>\n").append("    <integers>");

            // Format and write integers
            str = String.join("\"\"", expression.getIntegers().toString().split(", "));
            str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
            content.append(str).append("</integers>\n").append("    <doubles>");

            // Format and write doubles
            str = String.join("\"\"", expression.getDoubles().toString().split(", "));
            str = str.replaceAll("\\[", "\"").replaceAll("]", "\"");
            content.append(str).append("</doubles>\n")
                    .append("  </MathExpression>\n");
            writer.write(content.toString());
        }
        CloseWriter();
    }

    // Write a Result to the XML file
    public void WriteResult(Result result) throws IOException {
        OpenWriter();
        StringBuilder content = new StringBuilder();
        content.append("  <Result>\n")
                .append("    <result>").append(result.getResult()).append("</result>\n")
                .append("  </Result>\n");
        writer.write(content.toString());
        CloseWriter();
    }

    // Write a list of Results of MathExpressions to the XML file
    public void WriteListOfResultsOfMathExpressions(ArrayList<MathExpression> expressions, int type) throws IOException {
        ArrayList<Result> results = new ArrayList<>();
        for (MathExpression expression : expressions) {
            try {
                results.add(new Result(expression.Result(type)));
            } catch (IOException | Expression.ExpressionException | IllegalArgumentException exception) {
                results.add(new Result('e'));
            }
        }
        OpenWriter();
        for (Result result : results) {
            StringBuilder content = new StringBuilder();
            content.append("  <Result>\n")
                    .append("    <result>").append(result.getResult()).append("</result>\n")
                    .append("  </Result>\n");
            writer.write(content.toString());
        }
        CloseWriter();
    }
}
