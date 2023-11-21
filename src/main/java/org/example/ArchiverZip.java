package org.example;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.FileInputStream;
import java.io.IOException;

public class ArchiverZip
{
    private FileInputStream File_;
    static ZipArchiveInputStream ZipStream;
    ArchiverZip(String filename) throws IOException {
        File_ = new FileInputStream(filename);
        ZipStream = new ZipArchiveInputStream(File_);
    }
    public void CloseArchiverZip(String filename) throws IOException {
        File_.close();
        ZipStream.close();
    }

    public static void Archive() throws IOException {
        ArchiveEntry entry;
        while ((entry = ZipStream.getNextEntry()) != null) {
                // Обработка каждого файла в архиве
                // entry.getName() - имя файла
                // здесь можно выполнять необходимые действия с файлом
        }
    }
}
