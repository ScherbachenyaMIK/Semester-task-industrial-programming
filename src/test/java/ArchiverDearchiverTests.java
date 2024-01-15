import main.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArchiverDearchiverRARTest {

    @Test
    void ArchiveDearchiveFile() throws IOException, InterruptedException {
        String inputfilename = "test_input.txt";
        String arch_outputfilename = "arch_test_input.rar";
        String dearch_outputfilename = "arch_test_input/test_input.txt";
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
        DearchiverRar da = new DearchiverRar(arch_outputfilename);
        da.Dearchive();
        _BufferedFileReader bfr = new _BufferedFileReader(new _FileReader(dearch_outputfilename));
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
        file = new File(arch_outputfilename);
        if (file.exists()) {
            file.delete();
        }
        da.CloseDearchiverRar();
        file = new File(dearch_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void ArchiveDearchiveDirectory() throws IOException, InterruptedException {
        String inputfilesnames = "test_inputN/test_inputN.txt";
        String inputdirectoryname = "test_input/";
        String arch_outputfilename = "arch_test_input.rar";
        String dearch_outputfilename = "arch_test_input/";
        ArrayList<String> contents = new ArrayList<>();
        contents.add("aaaa\nbbbb\nmath");
        contents.add("russian\nbiology\ntask");
        contents.add("task\ntask\ntask");
        contents.add("x + y\nx * y\nx / y\nx * z");
        contents.add("archive");

        File file = new File(inputdirectoryname);
        if (file.exists()) {
            FileUtils.deleteDirectory(new File(inputdirectoryname));
        }
        file.mkdir();
        for(int i = 1; i <= 5; ++i) {
            file = new File(inputdirectoryname +
                    inputfilesnames.substring(0, 11).replaceFirst("N", Integer.toString(i)));
            file.mkdir();
            for (int j = 1; j <= 5; ++j) {
                _FileWriter fw = new _FileWriter(inputdirectoryname +
                        inputfilesnames.replaceFirst("N", Integer.toString(i))
                                .replaceFirst("N", Integer.toString(j)));
                fw.WriteString(contents.get(((j - 1) + (i - 1)) % 5));
                fw.CloseFile();
            }
        }
        ArchiverRar ar = new ArchiverRar(arch_outputfilename);
        ar.Archive(inputdirectoryname);
        DearchiverRar da = new DearchiverRar(arch_outputfilename);
        ArrayList<String> arc_contents = da.Dearchive();

        int l = 0;
        for(int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                String str;
                while ((str = arc_contents.get(l++)).endsWith("/"));
                assertTrue(str.startsWith(dearch_outputfilename + inputdirectoryname));
                _BufferedFileReader bfr = new _BufferedFileReader(new _FileReader(str));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bfr.ReadString()) != null) {
                    result.append(line).append('\n');
                }
                bfr.CloseFile();
                assertEquals(contents.get((i + j) % 5) + '\n', result.toString());
            }
        }

        file = new File(inputdirectoryname);
        if (file.exists()) {
            FileUtils.deleteDirectory(file);
        }
        file = new File(arch_outputfilename);
        if (file.exists()) {
            file.delete();
        }
        da.CloseDearchiverRar();
        file = new File(dearch_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void dearchiveFileIncorrectFileException() throws IOException, InterruptedException {
        String inputfilename = "test_input.txt";
        String dearch_outputfilename = "test_input/";
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
        try {
            DearchiverRar de = new DearchiverRar(inputfilename);
            de.Dearchive();
            de.CloseDearchiverRar();
            fail();
        } catch (IOException exception) {

        }
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(dearch_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void dearchiveFileNoFileException() throws InterruptedException {
        String inputfilename = "test_input.txt";
        String dearch_outputfilename = "test_input/";
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        try {
            DearchiverRar de = new DearchiverRar(inputfilename);
            de.Dearchive();
            de.CloseDearchiverRar();
            fail();
        } catch (IOException exception) {

        }
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(dearch_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void archiveFileNoFileException() throws InterruptedException {
        String inputfilename = "test_input.txt";
        String arch_outputfilename = "test_input.rar";
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        try {
            ArchiverRar ar = new ArchiverRar(inputfilename);
            ar.Archive(inputfilename);
            fail();
        } catch (IOException exception) {

        }
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

class ArchiverDearchiverZIPTest {

    @Test
    void ArchiveDearchiveFile() throws IOException {
        String inputfilename = "test_input.txt";
        String arch_outputfilename = "arch_test_input.zip";
        String dearch_outputfilename = "arch_test_input/test_input.txt";
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
        ar.closeArchiverZip();
        DearchiverZip da = new DearchiverZip(arch_outputfilename);
        da.Dearchive();

        _BufferedFileReader bfr = new _BufferedFileReader(new _FileReader(dearch_outputfilename));
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
        da.CloseDearchiverZip();
        file = new File(dearch_outputfilename);
        if (file.exists()) {
            fail();
        }
        file = new File(arch_outputfilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void ArchiveDearchiveDirectory() throws IOException {
        String inputfilesnames = "test_inputN/test_inputN.txt";
        String inputdirectoryname = "test_input/";
        String arch_outputfilename = "arch_test_input.zip";
        String dearch_outputfilename = "arch_test_input/";
        ArrayList<String> contents = new ArrayList<>();
        contents.add("aaaa\nbbbb\nmath");
        contents.add("russian\nbiology\ntask");
        contents.add("task\ntask\ntask");
        contents.add("x + y\nx * y\nx / y\nx * z");
        contents.add("archive");

        File file = new File(inputdirectoryname);
        if (file.exists()) {
            FileUtils.deleteDirectory(new File(inputdirectoryname));
        }
        file.mkdir();
        for(int i = 1; i <= 5; ++i) {
            file = new File(inputdirectoryname +
                    inputfilesnames.substring(0, 12).replaceFirst("N", Integer.toString(i)));
            file.mkdir();
            for (int j = 1; j <= 5; ++j) {
                _FileWriter fw = new _FileWriter(inputdirectoryname +
                        inputfilesnames.replaceFirst("N", Integer.toString(i))
                                .replaceFirst("N", Integer.toString(j)));
                fw.WriteString(contents.get(((j - 1) + (i - 1)) % 5));
                fw.CloseFile();
            }
        }
        ArchiverZip ar = new ArchiverZip(arch_outputfilename);
        ar.Archive(ArchiverZip.makeListOfFilesToArchive(inputdirectoryname));
        ar.closeArchiverZip();
        DearchiverZip da = new DearchiverZip(arch_outputfilename);
        ArrayList<String> arc_contents = da.Dearchive();

        int l = 0;
        for(int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                String str;
                while ((str = arc_contents.get(l++)).endsWith("/"));
                assertTrue(str.startsWith(dearch_outputfilename + inputdirectoryname));
                _BufferedFileReader bfr = new _BufferedFileReader(new _FileReader(str));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bfr.ReadString()) != null) {
                    result.append(line).append('\n');
                }
                bfr.CloseFile();
                assertEquals(contents.get((i + j) % 5) + '\n', result.toString());
            }
        }

        file = new File(inputdirectoryname);
        if (file.exists()) {
            FileUtils.deleteDirectory(file);
        }
        da.CloseDearchiverZip();
        file = new File(dearch_outputfilename);
        if (file.exists()) {
            fail();
        }
        file = new File(arch_outputfilename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void dearchiveFileIncorrectFileException() throws IOException {
        String inputfilename = "test_input.txt";
        String dearch_outputfilename = "test_input/";
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
        DearchiverZip de = new DearchiverZip(inputfilename);
        try {
            de.Dearchive();
            de.CloseDearchiverZip();
            fail();
        } catch (IOException exception) {
            de.CloseDearchiverZip();
        }
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(dearch_outputfilename);
        if (file.exists()) {
            FileUtils.deleteDirectory(file);
        }
    }

    @Test
    void dearchiveFileNoFileException() {
        String inputfilename = "test_input.txt";
        String dearch_outputfilename = "test_input/";
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        try {
            DearchiverZip de = new DearchiverZip(inputfilename);
            de.Dearchive();
            de.CloseDearchiverZip();
            fail();
        } catch (IOException exception) {

        }
        file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        file = new File(dearch_outputfilename);
        if (file.exists()) {
            fail();
        }
    }

    @Test
    void archiveFileNoFileException() throws IOException {
        String inputfilename = "test_input.txt";
        String arch_outputfilename = "test_input.zip";
        File file = new File(inputfilename);
        if (file.exists()) {
            file.delete();
        }
        ArchiverZip ar = new ArchiverZip(arch_outputfilename);
        try {
            ar.Archive(ArchiverZip.makeListOfFilesToArchive(inputfilename));
            ar.closeArchiverZip();
            fail();
        } catch (IOException exception) {
            ar.closeArchiverZip();
        }
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
    void makeListOfFilesToArchive() throws IOException {
        String inputfilesnames = "test_inputN/test_inputN.txt";
        String inputdirectoryname = "test_input/";
        ArrayList<String> contents = new ArrayList<>();
        contents.add("aaaa\nbbbb\nmath");
        contents.add("russian\nbiology\ntask");
        contents.add("task\ntask\ntask");
        contents.add("x + y\nx * y\nx / y\nx * z");
        contents.add("archive");
        contents.add("arc\nkein");
        contents.add("W\nT\nF");
        contents.add("Hello world!\nlong long\ndouble");
        contents.add("Big Integer\nBig Decimal");
        contents.add("Picture:\n _\n" + "/ \\\n| |\n___");

        File file = new File(inputdirectoryname);
        if (file.exists()) {
            FileUtils.deleteDirectory(new File(inputdirectoryname));
        }
        file.mkdir();
        long n = Math.round(Math.random()) % 10 + 1;
        long m = Math.round(Math.random()) % 10 + 1;
        for(int i = 1; i <= n; ++i) {
            file = new File(inputdirectoryname +
                    inputfilesnames.substring(0, 12).replaceFirst("N", Integer.toString(i)));
            file.mkdir();
            for (int j = 1; j <= m; ++j) {
                _FileWriter fw = new _FileWriter(inputdirectoryname +
                        inputfilesnames.replaceFirst("N", Integer.toString(i))
                                .replaceFirst("N", Integer.toString(j)));
                fw.WriteString(contents.get(((j - 1) + (i - 1)) % 10));
                fw.CloseFile();
            }
        }
        List<String> result = ArchiverZip.makeListOfFilesToArchive(inputdirectoryname);
        List<String> expected_result = new ArrayList<>();
        expected_result.add(inputdirectoryname);
        for (int i = 0; i < n; ++i) {
            expected_result.add(inputdirectoryname +
                    inputfilesnames.substring(0, 12).replaceFirst("N", Integer.toString(i + 1)));
            for (int j = 0; j < m; ++j) {
                expected_result.add(inputdirectoryname +
                        inputfilesnames.replaceFirst("N", Integer.toString(i + 1))
                                .replaceFirst("N", Integer.toString(j + 1)));
            }
        }
        assertEquals(result.size(), expected_result.size());
        for (int i = 0; i < result.size(); ++i) {
            assertEquals(expected_result.get(i), result.get(i));
        }

        file = new File(inputdirectoryname);
        if (file.exists()) {
            FileUtils.deleteDirectory(file);
        }
    }
}