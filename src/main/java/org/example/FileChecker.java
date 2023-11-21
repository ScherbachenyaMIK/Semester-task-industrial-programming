package org.example;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileChecker {
    static Pattern regex_zip = Pattern.compile("^PK");
    static Pattern regex_rar = Pattern.compile("^Rar!");
    static Pattern regex_XML = Pattern.compile("^<?xml");
    public static String CheckFormat(InputStream file) throws IOException {
        _BufferedFileReader FR = new _BufferedFileReader(file, "UTF-8"); //not working
        String str = FR.ReadString();
        switch(ParseString(str)) {
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
    static int ParseString(String str) {
        Matcher matcher = regex_zip.matcher(str);
        if(matcher.find())
        {
            return 1;   //ZIP
        }
        matcher = regex_rar.matcher(str);
        if(matcher.find())
        {
            return 2;   //RAR
        }
        matcher = regex_XML.matcher(str);
        if(matcher.find())
        {
            return 3;   //XML
        }
        if(isValidJSON(str))
        {
            return 4;   //JSON
        }

        return 5;   //TXT
    }
    static boolean isValidJSON(final String json) {
        boolean valid = false;
        try {
            final JsonParser parser = new ObjectMapper().getJsonFactory().createJsonParser(json);
            while (parser.nextToken() != null) {
            }
            valid = true;
        } catch (JsonParseException jpe) {
            //jpe.printStackTrace();
        } catch (IOException ioe) {
            //ioe.printStackTrace();
        }

        return valid;
    }
}
