import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileChecker {

    // Regular expressions to identify file formats
    static Pattern regex_zip = Pattern.compile("^PK");   // ZIP file signature
    static Pattern regex_rar = Pattern.compile("^Rar!");     // RAR file signature
    static Pattern regex_XML = Pattern.compile("^<?xml");   // XML file signature

    /**
     * Checks the format of the provided input stream.
     *
     * @param file InputStream of the file to be checked
     * @return String representing the file format (ZIP, RAR, XML, JSON, TXT, or Error)
     * @throws IOException if there is an error reading the file
     */
    public static String CheckFormat(InputStream file) throws IOException {
        // Read the first line of the file
        _BufferedFileReader FR = new _BufferedFileReader(file, "UTF-8");
        String str = FR.ReadString();

        // Determine the format based on the first line
        switch (ParseString(str)) {
            case 1:
                return "ZIP";
            case 2:
                return "RAR";
            case 3:
                return "XML";
            case 4:
                return "JSON";
            case 5:
                return "TXT";
        }

        return "Error!";
    }

    /**
     * Parses the provided string to identify its format.
     *
     * @param str String to be parsed
     * @return Integer representing the file format (1 for ZIP, 2 for RAR, 3 for XML, 4 for JSON, 5 for TXT)
     */
    static int ParseString(String str) {
        // Check if the string matches the ZIP file signature
        Matcher matcher = regex_zip.matcher(str);
        if (matcher.find()) {
            return 1;   // ZIP
        }

        // Check if the string matches the RAR file signature
        matcher = regex_rar.matcher(str);
        if (matcher.find()) {
            return 2;   // RAR
        }

        // Check if the string matches the XML file signature
        matcher = regex_XML.matcher(str);
        if (matcher.find()) {
            return 3;   // XML
        }

        // Check if the string is a valid JSON
        if (isValidJSON(str)) {
            return 4;   // JSON
        }

        return 5;   // TXT or encoded file
    }

    /**
     * Checks if the provided string is a valid JSON.
     *
     * @param json String to be checked
     * @return true if the string is a valid JSON, false otherwise
     */
    static boolean isValidJSON(final String json) {
        boolean valid = false;
        try {
            // Attempt to parse the string as JSON
            final JsonParser parser = new ObjectMapper().getJsonFactory().createJsonParser(json);
            while (parser.nextToken() != null) {
                // Iterate through the JSON tokens
            }
            valid = true;  // If parsing is successful, consider it valid
        } catch (JsonParseException jpe) {
            // Handle JSON parse exception (string is not valid JSON)
        } catch (IOException ioe) {
            // Handle IO exception
        }

        return valid;
    }
}
