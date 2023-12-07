package org.example;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

public class ArchiverZip
{
    private FileOutputStream File_;
    private ZipArchiveOutputStream ZipStream;
    ArchiverZip(String filename) throws IOException {
        File_ = new FileOutputStream(filename);
        ZipStream = new ZipArchiveOutputStream(File_);
    }
    public void CloseArchiverZip() throws IOException {
        File_.close();
        ZipStream.close();
    }

    public void Archive(List<String> filesToArchive, String directoryPath)
            throws IOException {
        for (String file : filesToArchive) {
            String filePath = directoryPath + file;
            ArchiveEntry entry = new ZipArchiveEntry(file);

            ZipStream.putArchiveEntry(entry);
            if (!file.endsWith("/")) {
                BufferedInputStream InputStream = new BufferedInputStream(new FileInputStream(filePath));
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = InputStream.read(buffer)) != -1) {
                    ZipStream.write(buffer, 0, bytesRead);
                }
                InputStream.close();
            }
            ZipStream.closeArchiveEntry();
        }
        ZipStream.finish();
    }
}
