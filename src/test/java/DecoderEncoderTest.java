import main.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidParameterSpecException;

import static org.junit.jupiter.api.Assertions.*;

class DecoderEncoderTest {

    @Test
    void encryptDecryptFile() throws IOException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        String inputfilename = "test_input.txt";
        String enc_outputfilename = "enc_test_input.txt";
        String dec_outputfilename = "dec_enc_test_input.txt";
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
        Decoder dec = new Decoder(enc_outputfilename, dec_outputfilename);
        dec.decryptFile("abcdefghabcdefgh");
        _BufferedFileReader bfr = new _BufferedFileReader(new _FileReader(dec_outputfilename));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = bfr.ReadString()) != null) {
            result.append(line).append('\n');
        }
        bfr.CloseFile();
        assertEquals(content + '\n', result.toString());
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(enc_outputfilename);
        if (file.exists()) {
            file.delete();
        }
        dec.closeDecoder();
        file = new File(dec_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void decryptFileInvalidKeyException() throws IOException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        String inputfilename = "test_input.txt";
        String enc_outputfilename = "enc_test_input.txt";
        String dec_outputfilename = "dec_enc_test_input.txt";
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
        Decoder dec = new Decoder(enc_outputfilename, dec_outputfilename);
        try {
            dec.decryptFile("1.5");
            fail();
        } catch (InvalidKeyException exception) {

        }
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(enc_outputfilename);
        if (file.exists()) {
            file.delete();
        }
        dec.closeDecoder();
        file = new File(dec_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void encryptFileInvalidKeyException() throws IOException, InvalidParameterSpecException {
        String inputfilename = "test_input.txt";
        String enc_outputfilename = "enc_test_input.txt";
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
        try {
            enc.encryptFile("a");
            fail();
        }
        catch (InvalidKeyException exception) {

        }
        enc.CloseEncoder();
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
    void decryptFileIncorrectKeyException() throws IOException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        String inputfilename = "test_input.txt";
        String enc_outputfilename = "enc_test_input.txt";
        String dec_outputfilename = "dec_enc_test_input.txt";
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
        Decoder dec = new Decoder(enc_outputfilename, dec_outputfilename);
        try {
            dec.decryptFile("aaaaaaaaaaaaaaaa");
            fail();
        } catch (IOException exception) {

        }
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(enc_outputfilename);
        if (file.exists()) {
            file.delete();
        }
        dec.closeDecoder();
        file = new File(dec_outputfilename);
        if (file.exists()) {
            fail();
        }
    }
    @Test
    void decryptFileIncorrectFileException() throws IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        String inputfilename = "test_input.txt";
        String dec_outputfilename = "dec_enc_test_input.txt";
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
        Decoder dec = new Decoder(inputfilename, dec_outputfilename);
        try {
            dec.decryptFile("aaaaaaaaaaaaaaaa");
            fail();
        } catch (IOException exception) {
            dec.closeDecoder();
        }
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(dec_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void decryptFileNoFileException() throws IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        String inputfilename = "test_input.txt";
        String dec_outputfilename = "dec_enc_test_input.txt";
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        try {
            Decoder dec = new Decoder(inputfilename, dec_outputfilename);
            dec.decryptFile("aaaaaaaaaaaaaaaa");
            dec.closeDecoder();
            fail();
        } catch (FileNotFoundException exception) {

        }
        file = new File(dec_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void encryptFileNoFileException() throws IOException, InvalidKeyException, InvalidParameterSpecException {
        String inputfilename = "test_input.txt";
        String enc_outputfilename = "dec_enc_test_input.txt";
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        try {
            Encoder enc = new Encoder(inputfilename, enc_outputfilename);
            enc.encryptFile("aaaaaaaaaaaaaaaa");
            enc.CloseEncoder();
            fail();
        } catch (FileNotFoundException exception) {

        }
        file = new File(enc_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void decryptFileIncorrectFileException2() throws IOException, InvalidKeyException, InvalidAlgorithmParameterException {
        String inputfilename = "test_input/";
        String dec_outputfilename = "dec_enc_test_input/";
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file.mkdir();
        try {
            Decoder dec = new Decoder(inputfilename, dec_outputfilename);
            dec.decryptFile("aaaaaaaaaaaaaaaa");
            dec.closeDecoder();
            fail();
        } catch (FileNotFoundException exception) {

        }
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(dec_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void encryptFileIncorrectFileException() throws IOException, InvalidKeyException, InvalidParameterSpecException {
        String inputfilename = "test_input/";
        String enc_outputfilename = "enc_test_input/";
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file.mkdir();
        try {
            Encoder enc = new Encoder(inputfilename, enc_outputfilename);
            enc.encryptFile("aaaaaaaaaaaaaaaa");
            enc.CloseEncoder();
            fail();
        } catch (FileNotFoundException exception) {

        }
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(enc_outputfilename);
        if (file.exists()) {
            fail();
        }
    }
}