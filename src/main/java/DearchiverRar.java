import org.apache.commons.io.FileUtils;
import java.io.*;
import java.util.ArrayList;

public class DearchiverRar {
    private String filename;
    private String OutputFiles;
    public DearchiverRar(String filename_) {
        filename = filename_;
    }
    public void CloseDearchiverRar() throws IOException {
        FileUtils.deleteDirectory(new File(OutputFiles));
    }
    public ArrayList<String> Dearchive() throws IOException, InterruptedException {
        ArrayList<String> files = new ArrayList<>();
        OutputFiles = filename.substring(0, filename.length() - 4) + "/";
        ProcessBuilder processBuilder = new ProcessBuilder("./UnRAR.exe", "x", "-o+", filename, OutputFiles);
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException();
        }

        String line;
        String file;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Creating")) {
                line = line.replaceAll(" ", "");
                file = line.substring(8, line.lastIndexOf("O"));
                file = file.replaceAll("\\\\", "/");
                files.add(file + "/");
            } else if (line.startsWith("Extracting") && !line.startsWith("Extracting from")) {
                line = line.replaceAll(" ", "");
                line = line.replaceAll("\b", "");
                line = line.replaceAll("\\d\\d?%", "");
                file = line.substring(10, line.lastIndexOf("O"));
                file = file.replaceAll("\\\\", "/");
                files.add(file);
            }
        }
        return files;
    }
}
