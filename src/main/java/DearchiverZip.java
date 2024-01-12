import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;

public class DearchiverZip {
    private FileInputStream File_;
    private ZipArchiveInputStream ZipStream;
    private String OutputFiles;
    DearchiverZip(String filename) throws IOException {
        File_ = new FileInputStream(filename);
        ZipStream = new ZipArchiveInputStream(File_);
        OutputFiles = filename.substring(0, filename.length() - 4) + "/";
    }
    public void CloseDearchiverZip() throws IOException {
        File_.close();
        ZipStream.close();
        FileUtils.deleteDirectory(new File(OutputFiles));
    }

    public ArrayList<String> Dearchive() throws IOException {
        ArrayList<String> files = new ArrayList<String>();
        ArchiveEntry entry;
        _BufferedFileReader fr = new _BufferedFileReader(ZipStream, "UTF8");
        files.add(OutputFiles);
        new File(OutputFiles).mkdirs();
        while ((entry = ZipStream.getNextEntry()) != null) {
            String name = OutputFiles + entry.getName();
            files.add(name);
            if (name.charAt(name.length() - 1) == '/'){
                new File(name).mkdirs();
            }
            else {
                _FileWriter fw = new _FileWriter(name);
                String str;
                while ((str = fr.ReadString()) != null) {
                    fw.WriteString(str);
                }
                fw.CloseFile();
            }
        }
        if (files.size() == 1) {
            throw new IOException("Error while working with .zip archive");
        }
        return files;
    }
}
