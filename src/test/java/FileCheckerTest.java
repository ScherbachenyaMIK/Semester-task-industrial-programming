import org.junit.jupiter.api.Test;

import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidParameterSpecException;

import static org.junit.jupiter.api.Assertions.*;

class FileCheckerTest {
    String outputPath = "TestOutput";
    @Test
    void checkTextFormat() throws IOException {
        String str = "AbRAcaDabrA";
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        _FileWriter FW = new _FileWriter(outputPath);
        FW.WriteString(str);
        FW.CloseFile();
        String fileFormat = FileChecker.CheckFormat(new FileInputStream(outputPath));
        assertEquals("TXT", fileFormat);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void checkJSONFormat() throws IOException {
        String str = "AbRAcaDabrA";
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonWriter XW = new JsonWriter(outputPath);
        XW.WriteString(str);
        String fileFormat = FileChecker.CheckFormat(new FileInputStream(outputPath));
        assertEquals("JSON", fileFormat);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void checkJSONFormat2() throws IOException {
        String str = "AbRAcaDabrA";
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        JsonNonAPIWriter JW = new JsonNonAPIWriter(outputPath);
        JW.WriteString(str);
        String fileFormat = FileChecker.CheckFormat(new FileInputStream(outputPath));
        assertEquals("JSON", fileFormat);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void checkXMLFormat() throws IOException {
        String str = "AbRAcaDabrA";
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLWriter XW = new XMLWriter(outputPath);
        XW.WriteString(str);
        String fileFormat = FileChecker.CheckFormat(new FileInputStream(outputPath));
        assertEquals("XML", fileFormat);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void checkXMLFormat2() throws IOException {
        String str = "AbRAcaDabrA";
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        XMLNonAPIWriter XW = new XMLNonAPIWriter(outputPath);
        XW.WriteString(str);
        String fileFormat = FileChecker.CheckFormat(new FileInputStream(outputPath));
        assertEquals("XML", fileFormat);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void checkEncodedFile() throws IOException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        String inputfilename = outputPath;
        String enc_outputfilename = "enc_" + outputPath;
        String content = """
            abc
            cab
            aaa
            abacaba
            """;
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        _FileWriter fw = new _FileWriter(inputfilename);
        fw.WriteString(content);
        fw.CloseFile();
        Encoder enc = new Encoder(inputfilename, enc_outputfilename);
        enc.encryptFile("abcdefghabcdefgh");
        enc.CloseEncoder();
        String fileFormat = FileChecker.CheckFormat(new FileInputStream(enc_outputfilename));
        assertEquals("TXT", fileFormat);
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(enc_outputfilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void CheckArchivedRARFile() throws IOException, InterruptedException {
        String inputfilename = outputPath;
        String arch_outputfilename = "arch_" + outputPath + ".rar";
        String content = """
            abc
            cab
            aaa
            abacaba
            """;
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        _FileWriter fw = new _FileWriter(inputfilename);
        fw.WriteString(content);
        fw.CloseFile();
        ArchiverRar ar = new ArchiverRar(arch_outputfilename);
        ar.Archive(inputfilename);
        String fileFormat = FileChecker.CheckFormat(new FileInputStream(arch_outputfilename));
        assertEquals("RAR", fileFormat);
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(arch_outputfilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void CheckArchivedZIPFile() throws IOException {
        String inputfilename = outputPath;
        String arch_outputfilename = "arch_" + outputPath;
        String content = """
            abc
            cab
            aaa
            abacaba
            """;
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        _FileWriter fw = new _FileWriter(inputfilename);
        fw.WriteString(content);
        fw.CloseFile();
        ArchiverZip ar = new ArchiverZip(arch_outputfilename);
        ar.Archive(ArchiverZip.makeListOfFilesToArchive(inputfilename));
        ar.CloseArchiverZip();
        String fileFormat = FileChecker.CheckFormat(new FileInputStream(arch_outputfilename));
        assertEquals("ZIP", fileFormat);
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(arch_outputfilename);
        if (file.exists()) {
            file.delete();
        }
    }
}