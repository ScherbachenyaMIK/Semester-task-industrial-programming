package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CLI {
    // Scanner to read System.in
    private final Scanner scanner = new Scanner(System.in);
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
        boolean correctInput = false;
        readerSource reader = null;

        refreshConsole();
        while (!correctInput) {
            try {
                // Choose the input file type
                int fileType = getUserChoice(
                        "Choose input file type:\n1) .txt\n2) .json\n3) .xml\n4) .zip\n5) .rar\n6) Encoded file\n7) Exit",7);
                char readerType;
                String filename = "";
                if (fileType < 4 && fileType > 0) {
                    // Get the input filename
                    if (reader != null) {
                        System.out.println("> Reader always has been defined");
                        System.out.println("> Press \"Enter\"...");
                        System.in.read();
                        continue;
                    }
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
                if (fileType > 3 && fileType < 7 && reader == null) {
                    System.out.println("> You should define reader at beginning");
                    System.out.println("> Press \"Enter\"...");
                    System.in.read();
                    continue;
                }
                switch (fileType) {
                    // Cases for different input file types
                    case 1:
                        // Text file
                        System.out.println("> Do you want to use Buffered Reader? (y/n)");
                        System.out.print("> ");
                        readerType = scanner.next().charAt(0);
                        if (readerType == 'y') {
                            reader = new _BufferedFileReader(new _FileReader(filename));
                        } else if (readerType == 'n') {
                            reader = new _FileReader(filename);
                        } else {
                            System.out.println("> Entered character incorrect");
                            System.out.println("> Defaulting to Reader without buffer");
                            reader = new _FileReader(filename);
                        }
                        break;
                    case 2:
                        // JSON file
                        System.out.println("> Do you want to use Non API Reader? (y/n)");
                        System.out.print("> ");
                        readerType = scanner.next().charAt(0);
                        if (readerType == 'y') {
                            reader = new JsonNonAPIReader(filename);
                        } else if (readerType == 'n') {
                            reader = new JsonReader(filename);
                        } else {
                            System.out.println("> Entered character incorrect");
                            System.out.println("> Defaulting to API Reader");
                            reader = new JsonReader(filename);
                        }
                        break;
                    case 3:
                        // XML file
                        System.out.println("> Do you want to use Non API Reader? (y/n)");
                        System.out.print("> ");
                        readerType = scanner.next().charAt(0);
                        if (readerType == 'y') {
                            reader = new XMLNonAPIReader(filename);
                        } else if (readerType == 'n') {
                            reader = new XMLReader(filename);
                        } else {
                            System.out.println("> Entered character incorrect");
                            System.out.println("> Defaulting to API Reader");
                            reader = new XMLReader(filename);
                        }
                        break;
                    case 4:
                        // ZIP archive
                        reader = new DearchiverZip(reader);
                        break;
                    case 5:
                        // RAR archive
                        reader = new DearchiverRar(reader);
                        break;
                    case 6:
                        // Encoded file
                        reader = new Decoder(reader);
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
            } catch (InputMismatchException exception) {
                // Handle input mismatch
                handleInputMismatchException();
            } catch (IOException | IllegalArgumentException exception) {
                // Handle input damaged
                handleFileDamagedException();
            }
        }
        // Handle reader not defined
        if (reader == null) {
            return;
        }
        try {
            readExpressions = reader.ReadListOfMathExpressions();
            mathExpressions.addAll(readExpressions);
            refreshConsole();
            System.out.println("> The file has been read successfully");
            System.out.println("> Press \"Enter\"...");
            System.out.print("> ");
            System.in.read();
            refreshConsole();
        } catch (IOException | IllegalArgumentException exception) {
            // Handle input damaged
            handleFileDamagedException();
        }
    }

    private void handleFileDamagedException() throws IOException {
        refreshConsole();
        System.out.println("> The file is damaged or does not match the format");
        System.out.println("> The file was not read");
        System.out.println("> Try again");
        System.out.println("> Press \"Enter\"...");
        System.out.print("> ");
        System.in.read();
        refreshConsole();
    }

    // Method to handle writing operations
    private void handleWriting() throws IOException {
        boolean correctInput = false;
        String filename = "";
        writerSource writer = null;
        int calculatorType = 0;

        refreshConsole();
        while (!correctInput) {
            try {
                // Choose the output file type
                int fileType = getUserChoice(
                        "Choose output file type:\n1) .txt\n2) .json\n3) .xml\n4) .zip\n5) .rar\n6) Encoded file\n7) Exit",
                        7);
                char writerType;

                if (fileType < 4 && fileType > 0) {
                    // Get the output filename
                    if (writer != null) {
                        System.out.println("> Writer always has been defined");
                        System.out.println("> Press \"Enter\"...");
                        System.in.read();
                        continue;
                    }
                    filename = getUserInput("Enter filename:");
                    if (filename.endsWith("/")) {
                        refreshConsole();
                        System.out.println("> Invalid filename entered");
                        System.out.println("> The file can't be read");
                        System.out.println("> Try again");
                        System.out.println("> Press \"Enter\"...");
                        System.in.read();
                        throw new IOException();
                    }
                    // Choose the calculator type for certain file types
                    calculatorType = getUserChoice("Choose calculator:\n1) Regex\n2) API\n3) Reversive Polish Notation", 3);
                }
                if (fileType > 3 && fileType < 7 && writer == null) {
                    System.out.println("> You should define writer at beginning");
                    System.out.println("> Press \"Enter\"...");
                    System.in.read();
                    continue;
                }
                switch (fileType) {
                    // Cases for different output file types
                    case 1:
                        // Text file
                        writer = new _FileWriter(filename);
                        break;
                    case 2:
                        // JSON file
                        System.out.println("> Do you want to use Non API Writer? (y/n)");
                        System.out.print("> ");
                        writerType = scanner.next().charAt(0);
                        if (writerType == 'y') {
                            writer = new JsonNonAPIWriter(filename);
                        } else if (writerType == 'n') {
                            writer = new JsonWriter(filename);
                        } else {
                            System.out.println("> Entered character incorrect");
                            System.out.println("> As default API Writer has been chosen");
                            writer = new JsonWriter(filename);
                        }
                        break;
                    case 3:
                        // XML file
                        System.out.println("> Do you want to use Non API Writer? (y/n)");
                        System.out.print("> ");
                        writerType = scanner.next().charAt(0);
                        if (writerType == 'y') {
                            writer = new XMLNonAPIWriter(filename);
                        } else if (writerType == 'n') {
                            writer = new XMLWriter(filename);
                        } else {
                            System.out.println("> Entered character incorrect");
                            System.out.println("> As default API Writer has been chosen");
                            writer = new XMLWriter(filename);
                        }
                        break;
                    case 4:
                        // ZIP archive file
                        writer = new ArchiverZip(writer);
                        break;
                    case 5:
                        // RAR archive file
                        writer = new ArchiverRar(writer);
                        break;
                    case 6:
                        // Encoded file
                        writer = new Encoder(writer);
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
        // Handle reader not defined
        if (writer == null) {
            return;
        }

        writer.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
        mathExpressions.clear();
        refreshConsole();
        System.out.println("> File successfully written");
        System.out.println("> Result in file " + filename);
        System.out.println("> Press \"Enter\"...");
        System.out.print("> ");
        System.in.read();
        refreshConsole();
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
    private void closeResources() {
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
