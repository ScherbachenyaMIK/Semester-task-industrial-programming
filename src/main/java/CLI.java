import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CLI {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<DearchiverZip> dearchiversZip = new ArrayList<>();
    private ArrayList<DearchiverRar> dearchiversRar = new ArrayList<>();
    private ArrayList<Decoder> decoders = new ArrayList<>();
    private ArrayList<MathExpression> mathExpressions = new ArrayList<>();
    public void StartCLI() throws IOException, InterruptedException {
        System.out.println("> Program run");
        Thread.sleep(1000);
        boolean quit = false;
        while (!quit) {
            try {
                boolean continueB = true;
                System.out.println("> Choose action");
                System.out.println("> 1) Reading");
                System.out.println("> 2) Writing");
                System.out.println("> 3) Stop");
                System.out.print("> ");
                int type = scanner.nextInt();
                char continueC;
                switch (type) {
                    case 1:
                        while (continueB) {
                            Reading();
                            System.out.println("> Do you want to continue reading? (y/n)");
                            System.out.print("> ");
                            continueC = scanner.next().charAt(0);
                            if (continueC == 'y') {
                                continueB = true;
                            } else if (continueC == 'n') {
                                continueB = false;
                            } else {
                                System.out.println("> Entered character incorrect");
                                System.out.println("> Reading stopped");
                                continueB = false;
                            }
                        }
                        break;
                    case 2:
                        while (continueB) {
                            Writing();
                            System.out.println("> Do you want to continue writing? (y/n)");
                            System.out.print("> ");
                            continueC = scanner.next().charAt(0);
                            if (continueC == 'y') {
                                continueB = true;
                            } else if (continueC == 'n') {
                                continueB = false;
                            } else {
                                System.out.println("> Entered character incorrect");
                                System.out.println("> Writing stopped");
                                continueB = false;
                            }
                        }
                        break;
                    case 3:
                        RefreshConsole();
                        quit = true;
                        break;
                    default:
                        RefreshConsole();
                        System.out.println("> Incorrect input!");
                        System.out.println("> Try again");
                        System.out.println("> Press \"Enter\"...");
                        System.out.print("> ");
                        System.in.read();
                        RefreshConsole();
                }
            }
            catch (InputMismatchException exception)
            {
                RefreshConsole();
                System.out.println("> Incorrect input!");
                System.out.println("> Try again");
                System.out.println("> Press \"Enter\"...");
                System.out.print("> ");
                System.in.read();
                scanner.next();
                RefreshConsole();
            }
        }

        for (var i : dearchiversZip)
        {
            i.CloseDearchiverZip();
        }
        for (var i : dearchiversRar)
        {
            i.CloseDearchiverRar();
        }
        for (var i : decoders)
        {
            i.closeDecoder();
        }
        System.out.println("> Program successfully completed");
        System.out.println("> All temps cleared");
        System.out.println("> Exiting...");
    }
    private void Reading() throws IOException {
        ArrayList<MathExpression> ReadedMEs = new ArrayList<>();
        ArrayList<String> dearchived_files;
        boolean correct_input = false;
        boolean exit = false;
        RefreshConsole();
        while (!correct_input) {
            try {
                System.out.println("> Choose input file type:");
                System.out.println("> 1) .txt");
                System.out.println("> 2) .json");
                System.out.println("> 3) .xml");
                System.out.println("> 4) .zip");
                System.out.println("> 5) .rar");
                System.out.println("> 6) Encoded file");
                System.out.println("> 7) Exit");
                System.out.print("> ");
                int file_type = scanner.nextInt();
                char reader_type;
                String filename = "";
                if (file_type < 7 && file_type > 0) {
                    System.out.println("> Enter filename:");
                    System.out.print("> ");
                    filename = scanner.next();
                    if (filename.endsWith("/")) {
                        RefreshConsole();
                        System.out.println("> Invalid filename have been entered");
                        System.out.println("> The file can't be read");
                        System.out.println("> Try again");
                        System.out.println("> Press \"Enter\"...");
                        System.out.print("> ");
                        System.in.read();
                        throw new IOException();
                    }
                }
                switch (file_type) {
                    case 1:
                        System.out.println("> Do you want to use Buffered Reader? (y/n)");
                        System.out.print("> ");
                        reader_type = scanner.next().charAt(0);
                        try {
                            if (reader_type == 'y') {
                                _BufferedFileReader bfr = new _BufferedFileReader(new _FileReader(filename));
                                ReadedMEs = bfr.ReadListOfMathExpressions();
                                bfr.CloseFile();
                            } else if (reader_type == 'n') {
                                _FileReader fr = new _FileReader(filename);
                                ReadedMEs = fr.ReadListOfMathExpressions();
                                fr.CloseFile();
                            } else {
                                System.out.println("> Entered character incorrect");
                                System.out.println("> As default Reader without buffer has been chosen");
                                _FileReader fr = new _FileReader(filename);
                                ReadedMEs = fr.ReadListOfMathExpressions();
                                fr.CloseFile();
                            }
                            correct_input = true;
                        }
                        catch (IllegalArgumentException exception) {
                            RefreshConsole();
                            System.out.println("> The text file is damaged or does not match the format");
                            System.out.println("> The file was not readed");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.out.print("> ");
                            System.in.read();
                            RefreshConsole();
                        }
                        break;
                    case 2:
                        System.out.println("> Do you want to use Non API Reader? (y/n)");
                        System.out.print("> ");
                        reader_type = scanner.next().charAt(0);
                        try {
                            if (reader_type == 'y') {
                                JsonNonAPIReader jnar = new JsonNonAPIReader(filename);
                                ReadedMEs = jnar.ReadListOfMathExpressions();
                                jnar.CloseJsonNonAPIReader();
                            } else if (reader_type == 'n') {
                                JsonReader jr = new JsonReader(filename);
                                ReadedMEs = jr.ReadListOfMathExpressions();
                            } else {
                                System.out.println("> Entered character incorrect");
                                System.out.println("> As default API Reader has been chosen");
                                JsonReader jr = new JsonReader(filename);
                                ReadedMEs = jr.ReadListOfMathExpressions();
                            }
                            correct_input = true;
                        }
                        catch (IllegalArgumentException | FileNotFoundException exception) {
                            RefreshConsole();
                            System.out.println("> The .json file is damaged or does not match the format");
                            System.out.println("> The file was not readed");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.out.print("> ");
                            System.in.read();
                            RefreshConsole();
                        }
                        break;
                    case 3:
                        System.out.println("> Do you want to use Non API Reader? (y/n)");
                        System.out.print("> ");
                        reader_type = scanner.next().charAt(0);
                        if (reader_type == 'y') {
                            XMLNonAPIReader xnar = new XMLNonAPIReader(filename);
                            ReadedMEs = xnar.ReadListOfMathExpressions();
                            xnar.CloseXMLNonAPIReader();
                        } else if (reader_type == 'n') {
                            XMLReader xr = new XMLReader(filename);
                            ReadedMEs = xr.ReadListOfMathExpressions();
                        } else {
                            System.out.println("> Entered character incorrect");
                            System.out.println("> As default API Reader has been chosen");
                            XMLReader xr = new XMLReader(filename);
                            ReadedMEs = xr.ReadListOfMathExpressions();
                        }
                        correct_input = true;
                        break;
                    case 4:
                        dearchiversZip.add(new DearchiverZip(filename));
                        dearchived_files = dearchiversZip.get(dearchiversZip.size() - 1).Dearchive();
                        RefreshConsole();
                        System.out.println("> File has been dearchived");
                        System.out.println("> file(s) collected in folder:");
                        System.out.println("> " + filename.substring(0, filename.length() - 4) + "/");
                        System.out.println("> Dearchived instances:");
                        for (String s : dearchived_files) {
                            System.out.println(s);
                        }
                        System.out.println("> Press \"Enter\"...");
                        System.out.print("> ");
                        System.in.read();
                        RefreshConsole();
                        correct_input = true;
                        exit = true;
                        break;
                    case 5:
                        dearchiversRar.add(new DearchiverRar(filename));
                        dearchived_files = dearchiversRar.get(dearchiversRar.size() - 1).Dearchive();   //TODO Resolve this catch
                        RefreshConsole();
                        System.out.println("> File has been dearchived");
                        System.out.println("> file(s) collected in folder:");
                        System.out.println("> " + filename.substring(0, filename.length() - 4) + "/");
                        System.out.println("> Dearchived instances:");
                        for (String s : dearchived_files) {
                            System.out.println(s);
                        }
                        System.out.println("> Press \"Enter\"...");
                        System.out.print("> ");
                        System.in.read();
                        RefreshConsole();
                        correct_input = true;
                        exit = true;
                        break;
                    case 6:
                        decoders.add(new Decoder(filename, "decripted_" + filename));
                        System.out.println("> Enter 16-digit encription key: ");
                        System.out.print("> ");
                        String key = scanner.next();
                        try {
                            decoders.get(decoders.size() - 1).decryptFile(key);
                            RefreshConsole();
                            System.out.println("> File has been decryped");
                            System.out.println("> Name of decrypted file:");
                            System.out.println("> decripted_" + filename);
                            correct_input = true;
                            exit = true;
                        } catch (IllegalArgumentException | InvalidKeyException | InvalidAlgorithmParameterException exception) {
                            RefreshConsole();
                            System.out.println("> Incorrect or invalid key have been entered");
                            System.out.println("> The file was not decoded");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.out.print("> ");
                            System.in.read();
                            RefreshConsole();
                        }
                        break;
                    case 7:
                        correct_input = true;
                        exit = true;
                        break;
                    default:
                        RefreshConsole();
                        System.out.println("> Incorrect input!");
                        System.out.println("> Try again");
                        System.out.println("> Press \"Enter\"...");
                        System.out.print("> ");
                        System.in.read();
                        RefreshConsole();
                }
            }
            catch (InputMismatchException exception)
            {
                RefreshConsole();
                System.out.println("> Incorrect input!");
                System.out.println("> Try again");
                System.out.println("> Press \"Enter\"...");
                System.out.print("> ");
                System.in.read();
                scanner.next();
                RefreshConsole();
            }
            catch (IOException exception)
            {
                RefreshConsole();
                System.out.println("> Error while working with file!");
                System.out.println("> Try again");
                System.out.println("> Press \"Enter\"...");
                System.out.print("> ");
                System.in.read();
                RefreshConsole();
            }
            catch (IllegalArgumentException exception) {
                RefreshConsole();
                System.out.println("> Error while working with xml file!");
                System.out.println("> Try again");
                System.out.println("> Press \"Enter\"...");
                System.out.print("> ");
                System.in.read();
                RefreshConsole();
            } catch (InterruptedException e) {  //TODO Resolve this catch
                throw new RuntimeException(e);
            }
        }

        if (!exit) {
            RefreshConsole();
            System.out.println("> File successfully readed!");
            System.out.println("> File content:");
            for (MathExpression i : ReadedMEs) {
                System.out.println(i.toString());
            }
            System.out.println("> Press \"Enter\"...");
            System.out.print("> ");
            System.in.read();
            RefreshConsole();

            mathExpressions.addAll(ReadedMEs);
        }
    }
    private void Writing() throws IOException {
        boolean correct_input = false;
        while (!correct_input) {
            try {
                System.out.println("> Choose output file type:");
                System.out.println("> 1) .txt");
                System.out.println("> 2) .json");
                System.out.println("> 3) .xml");
                System.out.println("> 4) .zip");
                System.out.println("> 5) .rar");
                System.out.println("> 6) Encoded file");
                System.out.println("> 7) Exit");
                System.out.print("> ");
                int file_type = scanner.nextInt();
                int calculator_type = 0;
                char writer_type;
                String filename = "";
                if (file_type < 7 && file_type > 0) {
                    System.out.println("> Enter filename:");
                    System.out.print("> ");
                    filename = scanner.next();
                    if (filename.endsWith("/")) {
                        filename = filename.concat(".out");
                    }
                }
                if (file_type < 4 && file_type > 0) {
                    System.out.println("> Choose calculator:");
                    System.out.println("> 1) Regex");
                    System.out.println("> 2) API");
                    System.out.println("> 3) Reversive Polish Notation");
                    System.out.print("> ");
                    calculator_type = scanner.nextInt();
                    switch (calculator_type) {
                        case 1:
                        case 2:
                        case 3:
                            break;
                        default:
                            RefreshConsole();
                            System.out.println("> Incorrect input!");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.out.print("> ");
                            System.in.read();
                            RefreshConsole();
                    }
                }
                String directoryPath;
                switch (file_type) {
                    case 1:
                        _FileWriter fw = new _FileWriter(filename);
                        fw.WriteListOfResultsOfMathExpressions(mathExpressions, calculator_type);
                        fw.CloseFile();
                        RefreshConsole();
                        System.out.println("> File successfully written");
                        System.out.println("> Result in file " + filename);
                        mathExpressions.clear();
                        correct_input = true;
                        break;
                    case 2:
                        System.out.println("> Do you want to use Non API Writer? (y/n)");
                        System.out.print("> ");
                        writer_type = scanner.next().charAt(0);
                        if (writer_type == 'y') {
                            JsonNonAPIWriter jnaw = new JsonNonAPIWriter(filename);
                            jnaw.WriteListOfResultsOfMathExpressions(mathExpressions, calculator_type);
                            jnaw.CloseJsonNonAPIWriter();
                        } else if (writer_type == 'n') {
                            JsonWriter jw = new JsonWriter(filename);
                            jw.WriteListOfResultsOfMathExpressions(mathExpressions, calculator_type);
                        } else {
                            System.out.println("> Entered character incorrect");
                            System.out.println("> As default API Writer have been chosen");
                            JsonWriter jw = new JsonWriter(filename);
                            jw.WriteListOfResultsOfMathExpressions(mathExpressions, calculator_type);
                        }
                        RefreshConsole();
                        System.out.println("> File successfully written");
                        System.out.println("> Result in file " + filename);
                        mathExpressions.clear();
                        correct_input = true;
                        break;
                    case 3:
                        System.out.println("> Do you want to use Non API Writer? (y/n)");
                        System.out.print("> ");
                        writer_type = scanner.next().charAt(0);
                        if (writer_type == 'y') {
                            XMLNonAPIWriter xnaw = new XMLNonAPIWriter(filename);
                            xnaw.WriteListOfResultsOfMathExpressions(mathExpressions, calculator_type);
                        } else if (writer_type == 'n') {
                            XMLWriter xw = new XMLWriter(filename);
                            xw.WriteListOfResultsOfMathExpressions(mathExpressions, calculator_type);
                            xw.CloseXMLWriter();
                        } else {
                            System.out.println("> Entered character incorrect");
                            System.out.println("> As default API Reader have been chosen");
                            XMLWriter xw = new XMLWriter(filename);
                            xw.WriteListOfResultsOfMathExpressions(mathExpressions, calculator_type);
                            xw.CloseXMLWriter();
                        }
                        RefreshConsole();
                        System.out.println("> File successfully written");
                        System.out.println("> Result in file " + filename);
                        mathExpressions.clear();
                        correct_input = true;
                        break;
                    case 4:
                        ArchiverZip az = new ArchiverZip(filename);
                        System.out.println("> Enter file path: ");
                        System.out.print("> ");
                        directoryPath = scanner.next();
                        try {
                            az.Archive(ArchiverZip.makeListOfFilesToArchive(directoryPath));
                            az.CloseArchiverZip();
                            RefreshConsole();
                            System.out.println("> File successfully archived");
                            System.out.println("> Result in file " + filename);
                            correct_input = true;
                        } catch (IOException exception) {
                            RefreshConsole();
                            System.out.println("> Error while working with .zip archive");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.out.print("> ");
                            System.in.read();
                            RefreshConsole();
                        }
                        break;
                    case 5:
                        ArchiverRar ar = new ArchiverRar(filename);
                        System.out.println("> Enter directory/file path: ");
                        System.out.print("> ");
                        directoryPath = scanner.next();
                        try {
                            ar.Archive(directoryPath);
                            RefreshConsole();
                            System.out.println("> File successfully archived");
                            System.out.println("> Result in file " + filename);
                            correct_input = true;
                        } catch (InterruptedException | IOException exception) {
                            RefreshConsole();
                            System.out.println("> Error while working with .rar archive");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.out.print("> ");
                            System.in.read();
                            RefreshConsole();
                        }
                        break;
                    case 6:
                        System.out.println("> Enter file path: ");
                        System.out.print("> ");
                        directoryPath = scanner.next();
                        Encoder enc = new Encoder(directoryPath, filename);
                        System.out.println("> Enter 16-digit key: ");
                        System.out.print("> ");
                        String key = scanner.next();
                        try {
                            if (key.length() != 16) {
                                throw new InvalidKeyException("Invalid key");
                            }
                            enc.encryptFile(key);
                            RefreshConsole();
                            System.out.println("> File successfully encoded");
                            System.out.println("> Result in file " + filename);
                            correct_input = true;
                        } catch (InvalidKeyException | InvalidParameterSpecException exception) {
                            RefreshConsole();
                            System.out.println("> Incorrect or invalid key have been entered");
                            System.out.println("> The file was not encoded");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.out.print("> ");
                            System.in.read();
                            RefreshConsole();
                        }
                        break;
                    case 7:
                        correct_input = true;
                        break;
                    default:
                        RefreshConsole();
                        System.out.println("> Incorrect input!");
                        System.out.println("> Try again");
                        System.out.println("> Press \"Enter\"...");
                        System.out.print("> ");
                        System.in.read();
                        RefreshConsole();
                }
            }
            catch (InputMismatchException exception)
            {
                RefreshConsole();
                System.out.println("> Incorrect input!");
                System.out.println("> Try again");
                System.out.println("> Press \"Enter\"...");
                System.out.print("> ");
                System.in.read();
                scanner.next();
                RefreshConsole();
            }
            catch (IOException exception)
            {
                RefreshConsole();
                System.out.println("> Error while working with file!");
                System.out.println("> Try again");
                System.out.println("> Press \"Enter\"...");
                System.out.print("> ");
                System.in.read();
                RefreshConsole();
            }
            catch (IllegalArgumentException exception) {
                RefreshConsole();
                System.out.println("> Error while working with xml file!");
                System.out.println("> Try again");
                System.out.println("> Press \"Enter\"...");
                System.out.print("> ");
                RefreshConsole();
            }
        }
    }
    private static void RefreshConsole() {
        for (int i = 0; i < 30; ++i) {
            System.out.print("\n");
        }
    }
}
