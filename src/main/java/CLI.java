import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CLI {
    // Scanner to read System.in
    private final Scanner scanner = new Scanner(System.in);
    // ArrayList to store dearchivers to safe temp files
    private final ArrayList<DearchiverZip> dearchiversZip = new ArrayList<>();
    // ArrayList to store dearchivers to safe temp files
    private final ArrayList<DearchiverRar> dearchiversRar = new ArrayList<>();
    // ArrayList to store decoders to safe temp files
    private final ArrayList<Decoder> decoders = new ArrayList<>();
    // ArrayList to store readed math expressions
    private final ArrayList<MathExpression> mathExpressions = new ArrayList<>();

    // Method to start the command-line interface
    public void startCLI() throws IOException, InterruptedException {
        System.out.println("> Program run");
        Thread.sleep(1000);
        boolean quit = false;
        while (!quit) {
            try {
                int type = getUserChoice("Choose action\n1) Reading\n2) Writing\n3) Write read contents\n4) Stop", 4);
                switch (type) {
                    case 1:
                        handleReading();
                        break;
                    case 2:
                        handleWriting();
                        break;
                    case 3:
                        WriteContents();
                        break;
                    case 4:
                        refreshConsole();
                        quit = true;
                        break;
                    default:
                        refreshConsole();
                        System.out.println("> Incorrect input!");
                        System.out.println("> Try again");
                        System.out.println("> Press \"Enter\"...");
                        System.in.read();
                        refreshConsole();
                }
            } catch (InputMismatchException exception) {
                handleInputMismatchException();
            }
        }
        closeResources();
    }

    // Method to handle reading operations
    private void handleReading() throws IOException {
        ArrayList<MathExpression> readExpressions = new ArrayList<>();
        ArrayList<String> dearchivedFiles;
        boolean correctInput = false;
        boolean exit = false;

        refreshConsole();
        while (!correctInput) {
            try {
                // Choose the input file type
                int fileType = getUserChoice(
                        "Choose input file type:\n1) .txt\n2) .json\n3) .xml\n4) .zip\n5) .rar\n6) Encoded file\n7) File checker" +
                                "\n8) Exit",8);
                char readerType;
                String filename = "";
                if (fileType < 8 && fileType > 0) {
                    // Get the input filename
                    System.out.println("> Enter filename:");
                    System.out.print("> ");
                    filename = scanner.next();
                    if (filename.endsWith("/")) {
                        refreshConsole();
                        System.out.println("> Invalid filename entered");
                        System.out.println("> The file can't be read");
                        System.out.println("> Try again");
                        System.out.println("> Press \"Enter\"...");
                        System.in.read();
                        throw new IOException();
                    }
                }
                switch (fileType) {
                    // Cases for different input file types
                    case 1:
                        // Text file
                        System.out.println("> Do you want to use Buffered Reader? (y/n)");
                        System.out.print("> ");
                        readerType = scanner.next().charAt(0);
                        try {
                            if (readerType == 'y') {
                                _BufferedFileReader bfr = new _BufferedFileReader(new _FileReader(filename));
                                readExpressions = bfr.ReadListOfMathExpressions();
                                bfr.CloseFile();
                            } else if (readerType == 'n') {
                                _FileReader fr = new _FileReader(filename);
                                readExpressions = fr.ReadListOfMathExpressions();
                                fr.CloseFile();
                            } else {
                                System.out.println("> Entered character incorrect");
                                System.out.println("> Defaulting to Reader without buffer");
                                _FileReader fr = new _FileReader(filename);
                                readExpressions = fr.ReadListOfMathExpressions();
                                fr.CloseFile();
                            }
                            correctInput = true;
                        } catch (IllegalArgumentException exception) {
                            refreshConsole();
                            System.out.println("> The text file is damaged or does not match the format");
                            System.out.println("> The file was not read");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                        }
                        break;
                    case 2:
                        // JSON file
                        System.out.println("> Do you want to use Non API Reader? (y/n)");
                        System.out.print("> ");
                        readerType = scanner.next().charAt(0);
                        try {
                            if (readerType == 'y') {
                                JsonNonAPIReader jnar = new JsonNonAPIReader(filename);
                                readExpressions = jnar.ReadListOfMathExpressions();
                                jnar.CloseReader();
                            } else if (readerType == 'n') {
                                JsonReader jr = new JsonReader(filename);
                                readExpressions = jr.ReadListOfMathExpressions();
                            } else {
                                System.out.println("> Entered character incorrect");
                                System.out.println("> Defaulting to API Reader");
                                JsonReader jr = new JsonReader(filename);
                                readExpressions = jr.ReadListOfMathExpressions();
                            }
                            correctInput = true;
                        } catch (IllegalArgumentException | FileNotFoundException exception) {
                            refreshConsole();
                            System.out.println("> The .json file is damaged or does not match the format");
                            System.out.println("> The file was not read");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                        }
                        break;
                    case 3:
                        // XML file
                        System.out.println("> Do you want to use Non API Reader? (y/n)");
                        System.out.print("> ");
                        readerType = scanner.next().charAt(0);
                        try {
                            if (readerType == 'y') {
                                XMLNonAPIReader xnar = new XMLNonAPIReader(filename);
                                readExpressions = xnar.ReadListOfMathExpressions();
                                xnar.CloseReader();
                            } else if (readerType == 'n') {
                                XMLReader xr = new XMLReader(filename);
                                readExpressions = xr.ReadListOfMathExpressions();
                            } else {
                                System.out.println("> Entered character incorrect");
                                System.out.println("> Defaulting to API Reader");
                                XMLReader xr = new XMLReader(filename);
                                readExpressions = xr.ReadListOfMathExpressions();
                            }
                            correctInput = true;
                        } catch (IllegalArgumentException | FileNotFoundException exception) {
                            refreshConsole();
                            System.out.println("> The .xml file is damaged or does not match the format");
                            System.out.println("> The file was not read");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                        }
                        break;
                    case 4:
                        // ZIP archive
                        try {
                            dearchiversZip.add(new DearchiverZip(filename));
                            dearchivedFiles = dearchiversZip.get(dearchiversZip.size() - 1).Dearchive();
                            refreshConsole();
                            System.out.println("> File has been dearchived");
                            System.out.println("> Files collected in folder: " + filename.substring(0, filename.length() - 4) + "/");
                            System.out.println("> Dearchived instances:");
                            for (String s : dearchivedFiles) {
                                System.out.println(s);
                            }
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                            correctInput = true;
                            exit = true;
                        } catch (IOException exception) {
                            refreshConsole();
                            System.out.println("> Error while working with .zip archive");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                        }
                        break;
                    case 5:
                        // RAR archive
                        try {
                            dearchiversRar.add(new DearchiverRar(filename));
                            dearchivedFiles = dearchiversRar.get(dearchiversRar.size() - 1).Dearchive();
                            refreshConsole();
                            System.out.println("> File has been dearchived");
                            System.out.println("> Files collected in folder: " + filename.substring(0, filename.length() - 4) + "/");
                            System.out.println("> Dearchived instances:");
                            for (String s : dearchivedFiles) {
                                System.out.println(s);
                            }
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                            correctInput = true;
                            exit = true;
                        } catch (IOException | InterruptedException exception) {
                            refreshConsole();
                            System.out.println("> Error while working with .rar archive");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                        }
                        break;
                    case 6:
                        // Encoded file
                        decoders.add(new Decoder(filename, "decripted_" + filename));
                        System.out.println("> Enter 16-digit encryption key: ");
                        System.out.print("> ");
                        String key = scanner.next();
                        try {
                            decoders.get(decoders.size() - 1).decryptFile(key);
                            refreshConsole();
                            System.out.println("> File has been decrypted");
                            System.out.println("> Name of decrypted file: decripted_" + filename);
                            correctInput = true;
                            exit = true;
                        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException exception) {
                            refreshConsole();
                            System.out.println("> Incorrect or invalid key entered");
                            System.out.println("> The file was not decrypted");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                        }
                        break;
                    case 7:
                        // Using of automatic file checker
                        System.out.println("> Warning!");
                        System.out.println("> Using of file checker may take a long time due to trying all possible options!");
                        try {
                            String fileFormat = FileChecker.CheckFormat(new FileInputStream(filename));
                            switch (fileFormat) {
                                case "ZIP":
                                    System.out.println("> I think the file '" + filename + "' may have the '.zip' format");
                                    System.out.println("> Try to use `ZipDearchiver` for reading");
                                    System.out.println("> Press \"Enter\"...");
                                    System.in.read();
                                    refreshConsole();
                                    break;
                                case "RAR":
                                    System.out.println("> I think the file '" + filename + "' may have the '.rar' format");
                                    System.out.println("> Try to use `RarDearchiver` for reading");
                                    System.out.println("> Press \"Enter\"...");
                                    System.in.read();
                                    refreshConsole();
                                    break;
                                case "XML":
                                    System.out.println("> I think the file '" + filename + "' may have the '.xml' format");
                                    System.out.println("> Try to use `XMLReader` for reading");
                                    System.out.println("> Press \"Enter\"...");
                                    System.in.read();
                                    refreshConsole();
                                    break;
                                case "JSON":
                                    System.out.println("> I think the file '" + filename + "' may have the '.json' format");
                                    System.out.println("> Try to use `JsonReader` for reading");
                                    System.out.println("> Press \"Enter\"...");
                                    System.in.read();
                                    refreshConsole();
                                    break;
                                case "TXT":
                                    System.out.println("> I think the file '" + filename + "' may be encoded or have the '.txt' format");
                                    System.out.println("> Try to use `Decoder` of 'TextFileReader' for reading");
                                    System.out.println("> Press \"Enter\"...");
                                    System.in.read();
                                    refreshConsole();
                                    break;
                                case "Error!":
                                default:
                                    throw new IOException("File has unknown format or does not exist");
                            }
                            correctInput = true;
                        } catch (IOException exception) {
                            refreshConsole();
                            System.out.println("> " + exception.getMessage());
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                        }
                        break;
                    case 8:
                        // Exit
                        correctInput = true;
                        break;
                    default:
                        // Handle invalid choice
                        handleInvalidChoice();
                }
                if (exit) {
                    break;
                }
            } catch (FileNotFoundException exception) {
                // Handle file not found
                handleFileNotFoundException();
            } catch (InputMismatchException | IOException | IllegalArgumentException exception) {
                // Handle input mismatch
                handleInputMismatchException();
            }
        }
        if (!exit) {
            mathExpressions.addAll(readExpressions);
        }
    }

    // Method to handle writing operations
    private void handleWriting() throws IOException {
        boolean correctInput = false;
        while (!correctInput) {
            try {
                // Choose the output file type
                int fileType = getUserChoice(
                        "Choose output file type:\n1) .txt\n2) .json\n3) .xml\n4) .zip\n5) .rar\n6) Encoded file\n7) Exit",
                        7);
                int calculatorType = 0;
                char writerType;
                String filename = "";

                if (fileType < 7 && fileType > 0) {
                    // Get the output filename
                    filename = getUserInput("Enter filename:");
                    if (filename.endsWith("/")) {
                        filename = filename.concat(".out");
                    }
                }
                if (fileType < 4 && fileType > 0) {
                    // Choose the calculator type for certain file types
                    calculatorType = getUserChoice("Choose calculator:\n1) Regex\n2) API\n3) Reversive Polish Notation", 3);
                }
                String directoryPath;
                switch (fileType) {
                    // Cases for different output file types
                    case 1:
                        _FileWriter fileWriter = new _FileWriter(filename);
                        fileWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
                        fileWriter.CloseFile();
                        refreshConsole();
                        System.out.println("> File successfully written");
                        System.out.println("> Result in file " + filename);
                        mathExpressions.clear();
                        correctInput = true;
                        break;
                    case 2:
                        // Choose the writer type for JSON
                        System.out.println("> Do you want to use Non API Writer? (y/n)");
                        System.out.print("> ");
                        writerType = scanner.next().charAt(0);
                        if (writerType == 'y') {
                            JsonNonAPIWriter jsonNonAPIWriter = new JsonNonAPIWriter(filename);
                            jsonNonAPIWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
                        } else if (writerType == 'n') {
                            JsonWriter jsonWriter = new JsonWriter(filename);
                            jsonWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
                        } else {
                            System.out.println("> Entered character incorrect");
                            System.out.println("> As default API Writer has been chosen");
                            JsonWriter jsonWriter = new JsonWriter(filename);
                            jsonWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
                        }
                        refreshConsole();
                        System.out.println("> File successfully written");
                        System.out.println("> Result in file " + filename);
                        mathExpressions.clear();
                        correctInput = true;
                        break;
                    case 3:
                        // Choose the writer type for XML
                        System.out.println("> Do you want to use Non API Writer? (y/n)");
                        System.out.print("> ");
                        writerType = scanner.next().charAt(0);
                        if (writerType == 'y') {
                            XMLNonAPIWriter xmlNonAPIWriter = new XMLNonAPIWriter(filename);
                            xmlNonAPIWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
                        } else if (writerType == 'n') {
                            XMLWriter xmlWriter = new XMLWriter(filename);
                            xmlWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
                        } else {
                            System.out.println("> Entered character incorrect");
                            System.out.println("> As default API Writer has been chosen");
                            XMLWriter xmlWriter = new XMLWriter(filename);
                            xmlWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
                        }
                        refreshConsole();
                        System.out.println("> File successfully written");
                        System.out.println("> Result in file " + filename);
                        mathExpressions.clear();
                        correctInput = true;
                        break;
                    case 4:
                        // Archive using ZIP
                        ArchiverZip archiverZip = new ArchiverZip(filename);
                        System.out.println("> Enter file path: ");
                        System.out.print("> ");
                        directoryPath = scanner.next();
                        try {
                            archiverZip.Archive(ArchiverZip.makeListOfFilesToArchive(directoryPath));
                            archiverZip.CloseArchiverZip();
                            refreshConsole();
                            System.out.println("> File successfully archived");
                            System.out.println("> Result in file " + filename);
                            correctInput = true;
                        } catch (IOException exception) {
                            refreshConsole();
                            System.out.println("> Error while working with .zip archive");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                        }
                        break;
                    case 5:
                        // Archive using RAR
                        ArchiverRar archiverRar = new ArchiverRar(filename);
                        System.out.println("> Enter directory/file path: ");
                        System.out.print("> ");
                        directoryPath = scanner.next();
                        try {
                            archiverRar.Archive(directoryPath);
                            refreshConsole();
                            System.out.println("> File successfully archived");
                            System.out.println("> Result in file " + filename);
                            correctInput = true;
                        } catch (InterruptedException | IOException exception) {
                            refreshConsole();
                            System.out.println("> Error while working with .rar archive");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                        }
                        break;
                    case 6:
                        // Encode using custom Encoder
                        System.out.println("> Enter file path: ");
                        System.out.print("> ");
                        directoryPath = scanner.next();
                        Encoder encoder = new Encoder(directoryPath, filename);
                        System.out.println("> Enter 16-digit key: ");
                        System.out.print("> ");
                        String key = scanner.next();
                        try {
                            if (key.length() != 16) {
                                throw new InvalidKeyException("Invalid key");
                            }
                            encoder.encryptFile(key);
                            encoder.CloseEncoder();
                            refreshConsole();
                            System.out.println("> File successfully encoded");
                            System.out.println("> Result in file " + filename);
                            correctInput = true;
                        } catch (InvalidKeyException | InvalidParameterSpecException exception) {
                            refreshConsole();
                            System.out.println("> Incorrect or invalid key have been entered");
                            System.out.println("> The file was not encoded");
                            System.out.println("> Try again");
                            System.out.println("> Press \"Enter\"...");
                            System.in.read();
                            refreshConsole();
                        }
                        break;
                    case 7:
                        // Exit
                        correctInput = true;
                        break;
                    default:
                        // Handle invalid choice
                        handleInvalidChoice();
                }
            } catch (FileNotFoundException exception) {
                // Handle file not found
                handleFileNotFoundException();
            } catch (InputMismatchException | IOException | IllegalArgumentException exception) {
                // Handle input mismatch
                handleInputMismatchException();
            }
        }
    }

    // Method to write math expressions been read
    private void WriteContents() throws IOException {
        refreshConsole();
        for (var i : mathExpressions) {
            System.out.println(i);
        }
        System.out.println("> Press \"Enter\"...");
        System.in.read();
        refreshConsole();
    }

    // Method to close resources and display exit message
    private void closeResources() throws IOException {
        for (DearchiverZip dearchiverZip : dearchiversZip) {
            dearchiverZip.CloseDearchiverZip();
        }
        for (DearchiverRar dearchiverRar : dearchiversRar) {
            dearchiverRar.CloseDearchiverRar();
        }
        for (Decoder decoder : decoders) {
            decoder.closeDecoder();
        }
        System.out.println("> Program successfully completed");
        System.out.println("> All temps cleared");
        System.out.println("> Exiting...");
    }

    // Method to refresh the console by printing new lines
    private static void refreshConsole() {
        for (int i = 0; i < 30; ++i) {
            System.out.print("\n");
        }
    }

    // Handle input mismatch
    private void handleInputMismatchException() throws IOException {
        refreshConsole();
        System.out.println("> Incorrect input!");
        System.out.println("> Try again");
        System.out.println("> Press \"Enter\"...");
        System.in.read();
        scanner.next();
        refreshConsole();
    }

    // Handle file not found
    private void handleFileNotFoundException() throws IOException {
        refreshConsole();
        System.out.println("> No such file exists");
        System.out.println("> Try again");
        System.out.println("> Press \"Enter\"...");
        System.in.read();
        refreshConsole();
    }

    // Method return user choice
    private int getUserChoice(String message, int maxChoice) throws IOException {
        int choice;
        while (true) {
            try {
                System.out.println(message);
                System.out.print("> ");
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= maxChoice) {
                    return choice;
                }
                handleInvalidChoice();
            } catch (InputMismatchException | IOException exception) {
                handleInputMismatchException();
            }
        }
    }

    // Method return user input
    private String getUserInput(String message) {
        System.out.println(message);
        System.out.print("> ");
        return scanner.next();
    }

    // Handle invalid choice
    private void handleInvalidChoice() throws IOException {
        refreshConsole();
        System.out.println("> Invalid choice!");
        System.out.println("> Try again");
        System.out.println("> Press \"Enter\"...");
        System.in.read();
        refreshConsole();
    }
}
