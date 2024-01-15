//package UI;
//
//import com.udojava.evalex.Expression;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.stage.DirectoryChooser;
//import javafx.stage.FileChooser;
//import main.*;
//
//import java.io.*;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//import java.security.spec.InvalidParameterSpecException;
//import java.util.ArrayList;
//import java.util.Objects;
//import java.util.Optional;
//
//public class JavaFxController {
//    @FXML
//    private TableView<TableRowData> resultTable = new TableView<TableRowData>();
//    @FXML
//    private TableColumn<TableRowData, String> mathExpressionColumn = new TableColumn<>("Math expression");
//    @FXML
//    private TableColumn<TableRowData, String> regexResultColumn = new TableColumn<>("Regex Result");
//    @FXML
//    private TableColumn<TableRowData, String> rpnResultColumn = new TableColumn<>("RPN Result");
//    @FXML
//    private TableColumn<TableRowData, String> apiResultColumn = new TableColumn<>("API Result");
//
//    @FXML
//    private ToggleGroup radioGroupReading;
//
//    @FXML
//    private ToggleGroup radioGroupWriting;
//
//    @FXML
//    private ListView<RadioButtonListItem> radioListViewReading;
//
//    @FXML
//    private ListView<RadioButtonListItem> radioListViewWriting;
//
//    @FXML
//    private ToggleGroup radioGroupOptionsReading;
//
//    @FXML
//    private ToggleGroup radioGroupOptionsWriting;
//
//    @FXML
//    private Label labelOptionsReading;
//
//    @FXML
//    private ListView<RadioButtonListItem> radioListViewOptionsReading;
//
//    @FXML
//    private Label labelOptionsWriting;
//
//    @FXML
//    private ListView<RadioButtonListItem> radioListViewOptionsWriting;
//
//    @FXML    private Button btnSelectCatReading;
//
//    @FXML    private Button btnSelectCatWriting;
//
//    @FXML    private TextField textFieldSelectCatReading;
//
//    @FXML    private TextField textFieldSelectCatWriting;
//
//    public void initialize() {
//        setResultTable();
//        setRadioGroupReading();
//        setRadioGroupWriting();
//        setTextFieldSelectCatReading();
//        setTextFieldSelectCatWriting();
//    }
//
//    @FXML
//    private void setResultTable()
//    {
//        mathExpressionColumn.setCellValueFactory(cellData -> cellData.getValue().mathExpressionProperty());
//        mathExpressionColumn.setPrefWidth(265);
//        resultTable.getColumns().add(mathExpressionColumn);
//        regexResultColumn.setCellValueFactory(cellData -> cellData.getValue().regexResultProperty());
//        regexResultColumn.setPrefWidth(100);
//        resultTable.getColumns().add(regexResultColumn);
//        rpnResultColumn.setCellValueFactory(cellData -> cellData.getValue().rpnResultProperty());
//        rpnResultColumn.setPrefWidth(100);
//        resultTable.getColumns().add(rpnResultColumn);
//        apiResultColumn.setCellValueFactory(cellData -> cellData.getValue().apiResultProperty());
//        apiResultColumn.setPrefWidth(100);
//        resultTable.getColumns().add(apiResultColumn);
//    }
//
//    @FXML
//    private void addRow(MathExpression mathExpression) {
//        String regex;
//        try {
//            regex = mathExpression.Result(1);
//        } catch (IOException | NumberFormatException e) {
//            regex = "Error!";
//        }
//        String api;
//        try {
//            api = mathExpression.Result(2);
//        } catch (IOException | Expression.ExpressionException e) {
//            api = "Error!";
//        }
//        String rpn;
//        try {
//            rpn = mathExpression.Result(3);
//        } catch (IOException | IllegalArgumentException e) {
//            rpn = "Error!";
//        }
//        TableRowData newRow = new TableRowData(mathExpression.toString(), regex, rpn, api);
//        resultTable.getItems().add(newRow);
//    }
//
//    @FXML
//    private void removeLastRow() {
//        int size = resultTable.getItems().size();
//        if (size > 0) {
//            resultTable.getItems().remove(size - 1);
//        }
//        mathExpressions.remove(mathExpressions.size() - 1);
//    }
//
//    @FXML
//    private void removeAllRows() {
//        resultTable.getItems().clear();
//        mathExpressions.clear();
//    }
//
//    @FXML
//    private void removeSelectedRows() {
//        ObservableList<TableRowData> selectedItems = resultTable.getSelectionModel().getSelectedItems();
//        mathExpressions.remove(resultTable.getSelectionModel().getSelectedIndex());
//        resultTable.getItems().removeAll(selectedItems);
//    }
//
//    @FXML
//    private void setRadioGroupReading()
//    {
//        radioGroupReading = new ToggleGroup();
//        radioGroupReading.selectedToggleProperty();
//        radioGroupOptionsReading = new ToggleGroup();
//        radioGroupOptionsReading.selectedToggleProperty();
//        radioListViewOptionsReading.setVisible(false);
//        radioListViewOptionsReading.prefHeightProperty().set(0);
//        labelOptionsReading.setVisible(false);
//        labelOptionsReading.prefHeightProperty().set(0);
//
//        radioListViewReading.getItems().add(new RadioButtonListItem("Read from txt", radioGroupReading));
//        radioListViewReading.getItems().add(new RadioButtonListItem("Read from json", radioGroupReading));
//        radioListViewReading.getItems().add(new RadioButtonListItem("Read from xml", radioGroupReading));
//        radioListViewReading.getItems().add(new RadioButtonListItem("Dearchive zip file", radioGroupReading));
//        radioListViewReading.getItems().add(new RadioButtonListItem("Dearchive rar file", radioGroupReading));
//        radioListViewReading.getItems().add(new RadioButtonListItem("Decode file", radioGroupReading));
//        radioListViewReading.getItems().add(new RadioButtonListItem("Check file format", radioGroupReading));
//        radioListViewReading.setFixedCellSize(30);
//        radioListViewReading.prefHeightProperty().bind(radioListViewReading.fixedCellSizeProperty().multiply(7).add(2));
//
//        radioListViewReading.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                RadioButton selectedRadioButton = newValue.getRadioButton();
//                selectedRadioButton.setSelected(true);
//                updateReadButton();
//            }
//        });
//        radioGroupReading.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                changeReaderOptions(((RadioButton) newValue).getText());
//                updateReadButton();
//            }
//        });
//        radioListViewOptionsReading.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                RadioButton selectedRadioButton = newValue.getRadioButton();
//                selectedRadioButton.setSelected(true);
//                updateReadButton();
//            }
//        });
//        radioGroupOptionsReading.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                updateReadButton();
//            }
//        });
//    }
//
//    @FXML
//    private void setRadioGroupWriting()
//    {
//        radioGroupWriting = new ToggleGroup();
//        radioGroupWriting.selectedToggleProperty();
//        radioGroupOptionsWriting = new ToggleGroup();
//        radioGroupOptionsWriting.selectedToggleProperty();
//        radioListViewOptionsWriting.setVisible(false);
//        radioListViewOptionsWriting.prefHeightProperty().set(0);
//        labelOptionsWriting.setVisible(false);
//        labelOptionsWriting.prefHeightProperty().set(0);
//
//        radioListViewWriting.getItems().add(new RadioButtonListItem("Write to txt", radioGroupWriting));
//        radioListViewWriting.getItems().add(new RadioButtonListItem("Write to json", radioGroupWriting));
//        radioListViewWriting.getItems().add(new RadioButtonListItem("Write to xml", radioGroupWriting));
//        radioListViewWriting.getItems().add(new RadioButtonListItem("Archive to zip file", radioGroupWriting));
//        radioListViewWriting.getItems().add(new RadioButtonListItem("Archive to rar file", radioGroupWriting));
//        radioListViewWriting.getItems().add(new RadioButtonListItem("Encode file", radioGroupWriting));
//        radioListViewWriting.setFixedCellSize(30);
//        radioListViewWriting.prefHeightProperty().bind(radioListViewWriting.fixedCellSizeProperty().multiply(6).add(2));
//
//        radioListViewWriting.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                RadioButton selectedRadioButton = newValue.getRadioButton();
//                selectedRadioButton.setSelected(true);
//                updateWriteButton();
//            }
//        });
//        radioGroupWriting.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                changeWriterOptions(((RadioButton) newValue).getText());
//                updateWriteButton();
//            }
//        });
//        radioListViewOptionsWriting.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                RadioButton selectedRadioButton = newValue.getRadioButton();
//                selectedRadioButton.setSelected(true);
//                updateWriteButton();
//            }
//        });
//        radioGroupOptionsWriting.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                updateWriteButton();
//            }
//        });
//    }
//
//    @FXML
//    private Button readButton;
//
//    private void updateReadButton() {
//        readButton.setDisable(radioGroupReading.getSelectedToggle() == null ||
//                radioGroupOptionsReading.getSelectedToggle() == null && !radioListViewOptionsReading.getItems().isEmpty() ||
//                Objects.equals(textFieldSelectCatReading.getText(), ""));
//    }
//
//    @FXML
//    private void handleReadButton(ActionEvent event) {
//        Read();
//    }
//
//    @FXML
//    private void handleWriteButton(ActionEvent event) {
//        Write();
//    }
//
//    private void showErrorDialog(String headerText, String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Error!");
//        alert.setHeaderText(headerText);
//        alert.setContentText(message);
//        alert.setResizable(true);
//
//        alert.showAndWait();
//    }
//
//    private void showWarningDialog(String headerText, String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Warning!");
//        alert.setHeaderText(headerText);
//        alert.setContentText(message);
//        alert.setResizable(true);
//
//        alert.showAndWait();
//    }
//
//    private void showInformationDialogReading() {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Success");
//        alert.setHeaderText("Successful reading");
//        alert.setContentText("File was successfully readed, check updates in a table!");
//        alert.setResizable(true);
//
//        alert.showAndWait();
//    }
//
//    private void showInformationDialogDearchiving(ArrayList<String> dearchivedFiles) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Success");
//        alert.setHeaderText("Successful dearchiving");
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String s : dearchivedFiles) {
//            stringBuilder.append(s).append('\n');
//        }
//        alert.setContentText("Dearchived instances:\n" + stringBuilder.toString());
//        alert.setResizable(true);
//
//        alert.showAndWait();
//    }
//
//    private void showInformationDialogWriting() {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Success");
//        alert.setHeaderText("Successful written");
//        alert.setContentText("File was successfully written, check updates in a system!");
//        alert.setResizable(true);
//
//        alert.showAndWait();
//    }
//
//    private void showInformationDialogArchiving(String output) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Success");
//        alert.setHeaderText("Successful archiving");
//        alert.setContentText("Archive name is '" + output + "', check updates in a system!");
//        alert.setResizable(true);
//
//        alert.showAndWait();
//    }
//
//    private String key;
//
//    private void showKeyInputDialog() {
//        Dialog<String> keyDialog = new Dialog<>();
//        keyDialog.setTitle("Key Input Dialog");
//        keyDialog.setHeaderText("Enter your 16-digit key:");
//
//        TextField keyField = new TextField();
//        keyField.setPromptText("Key:");
//
//        // Create TextFormatter with a UnaryOperator for filtering input characters
//        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
//            String newText = change.getControlNewText();
//            if (newText.matches(".{0,16}")) {
//                return change;
//            } else {
//                return null; // Reject the changes
//            }
//        });
//
//        keyField.setTextFormatter(textFormatter);
//
//        keyDialog.getDialogPane().setContent(keyField);
//
//        keyDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
//
//        keyDialog.setResultConverter(buttonType -> {
//            if (buttonType == ButtonType.OK) {
//                String enteredKey = keyField.getText();
//                if (enteredKey.length() == 16) {
//                    key = enteredKey;
//                    return enteredKey;
//                } else {
//                    showErrorDialog("Invalid Key", "Please enter a 16-digit key.");
//                }
//            }
//            return null;
//        });
//
//        keyDialog.showAndWait();
//    }
//
//    private void showInformationDialogEncoding(String output) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Success");
//        alert.setHeaderText("Successful decoding");
//        alert.setContentText("You can find result in file '" + output + "'");
//        alert.setResizable(true);
//
//        alert.showAndWait();
//    }
//
//    private void showInformationDialogFormatChecker(String format, String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Success");
//        alert.setHeaderText("Successful checking (" + format + ")");
//        alert.setContentText(message);
//        alert.setResizable(true);
//
//        alert.showAndWait();
//    }
//
//    private int showCalculatorChooser() {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Calculator chooser");
//        alert.setHeaderText("Choose calculator type:");
//
//        ButtonType buttonRegex = new ButtonType("Regex");
//        ButtonType buttonAPI = new ButtonType("API");
//        ButtonType buttonRPN = new ButtonType("RPN");
//
//        alert.getButtonTypes().setAll(buttonRegex, buttonAPI, buttonRPN, ButtonType.CANCEL);
//
//        Optional<ButtonType> result = alert.showAndWait();
//        ButtonType buttonType;
//        if (result.isPresent()) {
//            buttonType = result.get();
//            if (buttonType == buttonRegex) {
//                return 1;
//            }
//            if (buttonType == buttonAPI) {
//                return 2;
//            }
//            if (buttonType == buttonRPN) {
//                return 3;
//            }
//        }
//        return 0;
//    }
//
//    private String showFileDirectoryChooser() {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Dialog");
//        alert.setHeaderText("Choose an option:");
//
//        ButtonType buttonFileType = new ButtonType("File");
//        ButtonType buttonDirectoryType = new ButtonType("Directory");
//
//        alert.getButtonTypes().setAll(buttonFileType, buttonDirectoryType, ButtonType.CANCEL);
//
//        Optional<ButtonType> result = alert.showAndWait();
//        File selected;
//        String resultpath;
//
//        if (result.isPresent() && result.get() == buttonFileType) {
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Opening");
//            File initialDirectory = new File(System.getProperty("user.home"));
//            fileChooser.setInitialDirectory(initialDirectory);
//            selected = fileChooser.showOpenDialog(resultTable.getScene().getWindow());
//            resultpath = selected.getAbsolutePath();
//        } else if (result.isPresent() && result.get() == buttonDirectoryType) {
//            DirectoryChooser directoryChooser = new DirectoryChooser();
//            directoryChooser.setTitle("Select Directory");
//            File initialDirectory = new File(System.getProperty("user.home"));
//            directoryChooser.setInitialDirectory(initialDirectory);
//            selected = directoryChooser.showDialog(resultTable.getScene().getWindow());
//            resultpath = selected.getAbsolutePath() + '/';
//        } else {
//            return null;
//        }
//
//        return resultpath;
//    }
//
//    private String showFileChooser() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Opening");
//        File initialDirectory = new File(System.getProperty("user.home"));
//        fileChooser.setInitialDirectory(initialDirectory);
//
//        return fileChooser.showOpenDialog(resultTable.getScene().getWindow()).getAbsolutePath();
//    }
//
//    // ArrayList to store dearchivers to safe temp files
//    private final ArrayList<DearchiverZip> dearchiversZip = new ArrayList<>();
//    // ArrayList to store dearchivers to safe temp files
//    private final ArrayList<DearchiverRar> dearchiversRar = new ArrayList<>();
//    // ArrayList to store decoders to safe temp files
//    private final ArrayList<Decoder> decoders = new ArrayList<>();
//    // ArrayList to store readed math expressions
//    ArrayList<MathExpression> mathExpressions = new ArrayList<>();
//
//    private void Read() {
//        ArrayList<MathExpression> readExpressions = new ArrayList<>();
//        ArrayList<String> dearchivedFiles;
//        RadioButton selectedRadioButton = (RadioButton) radioGroupReading.getSelectedToggle();
//        String fileType = selectedRadioButton.getText();
//        selectedRadioButton = (RadioButton) radioGroupOptionsReading.getSelectedToggle();
//        String option;
//        if (selectedRadioButton != null) {
//            option = selectedRadioButton.getText();
//        }
//        else {
//            option = null;
//        }
//        String filename = textFieldSelectCatReading.getText();
//        switch (fileType) {
//            // Cases for different input file types
//            case "Read from txt":
//                // text file
//                try {
//                    if (Objects.equals(option, "Buffered reader")) {
//                        _BufferedFileReader bfr = new _BufferedFileReader(new _FileReader(filename));
//                        readExpressions = bfr.ReadListOfMathExpressions();
//                        bfr.CloseFile();
//                        showInformationDialogReading();
//                    } else {
//                        _FileReader fr = new _FileReader(filename);
//                        readExpressions = fr.ReadListOfMathExpressions();
//                        fr.CloseFile();
//                        showInformationDialogReading();
//                    }
//                } catch (IllegalArgumentException exception) {
//                    showErrorDialog("Error while working with text file",
//                            "The text file is damaged or does not match the format\nThe file was not read");
//                } catch (FileNotFoundException exception) {
//                    showErrorDialog("Error while working with text file",
//                            "The text file was not found\nThe file was not read");
//                } catch (IOException exception) {
//                    showErrorDialog("Error while working with text file",
//                            "Error while working text file\nThe file was not read");
//                }
//                break;
//            case "Read from json":
//                // JSON file
//                try {
//                    if (Objects.equals(option, "Non API reader")) {
//                        JsonNonAPIReader jnar = new JsonNonAPIReader(filename);
//                        readExpressions = jnar.ReadListOfMathExpressions();
//                        jnar.CloseReader();
//                        showInformationDialogReading();
//                    } else {
//                        JsonReader jr = new JsonReader(filename);
//                        readExpressions = jr.ReadListOfMathExpressions();
//                        showInformationDialogReading();
//                    }
//                } catch (IllegalArgumentException exception) {
//                    showErrorDialog("Error while working with json file",
//                            "The json file is damaged or does not match the format\nThe file was not read");
//                } catch (FileNotFoundException exception) {
//                    showErrorDialog("Error while working with json file",
//                            "The json file was not found\nThe file was not read");
//                } catch (IOException exception) {
//                    showErrorDialog("Error while working with json file",
//                            "Error while working json file\nThe file was not read");
//                }
//                break;
//                    case "Read from xml":
//                        // XML file
//                        try {
//                            if (Objects.equals(option, "Non API reader")) {
//                                XMLNonAPIReader xnar = new XMLNonAPIReader(filename);
//                                readExpressions = xnar.ReadListOfMathExpressions();
//                                xnar.CloseReader();
//                                showInformationDialogReading();
//                            } else {
//                                XMLReader xr = new XMLReader(filename);
//                                readExpressions = xr.ReadListOfMathExpressions();
//                                showInformationDialogReading();
//                            }
//                        } catch (IllegalArgumentException exception) {
//                            showErrorDialog("Error while working with xml file",
//                                    "The xml file is damaged or does not match the format\nThe file was not read");
//                        } catch (FileNotFoundException exception) {
//                            showErrorDialog("Error while working with xml file",
//                                    "The xml file was not found\nThe file was not read");
//                        } catch (IOException exception) {
//                            showErrorDialog("Error while working with xml file",
//                                    "Error while working xml file\nThe file was not read");
//                        }
//                        break;
//                    case "Dearchive zip file":
//                        // ZIP archive
//                        try {
//                            dearchiversZip.add(new DearchiverZip(filename));
//                            dearchivedFiles = dearchiversZip.get(dearchiversZip.size() - 1).Dearchive();
//                            showInformationDialogDearchiving(dearchivedFiles);
//                        } catch (IOException exception) {
//                            showErrorDialog("Error while working with zip file",
//                                    "Error while working zip file\nThe file was not dearchived");
//                        }
//                        break;
//                    case "Dearchive rar file":
//                        // RAR archive
//                        try {
//                            dearchiversRar.add(new DearchiverRar(filename));
//                            dearchivedFiles = dearchiversRar.get(dearchiversRar.size() - 1).Dearchive();
//                            showInformationDialogDearchiving(dearchivedFiles);
//                        } catch (IOException | InterruptedException exception) {
//                            showErrorDialog("Error while working with rar file",
//                                    "Error while working rar file\nThe file was not dearchived");
//                        }
//                        break;
//                    case "Decode file":
//                        // Encoded file
//                        showKeyInputDialog();
//                        if (key == null) {
//                            break;
//                        }
//                        try {
//                            decoders.add(new Decoder(filename, filename.substring(0, filename.lastIndexOf('\\') + 1) +
//                                    "decripted_" + filename.substring(filename.lastIndexOf('\\') + 1)));
//                            decoders.get(decoders.size() - 1).decryptFile(key);
//                            showInformationDialogEncoding("decripted_" + filename);
//                        } catch (FileNotFoundException exception) {
//                            showErrorDialog("Error while working with encoded file",
//                                    "The encoded file was not found\nThe file was not decoded");
//                        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException exception) {
//                            showErrorDialog("Error while working with encoded file",
//                                    "Error while working encoded file\nThe file was not decoded");
//                        }
//                        break;
//                    case "Check file format":
//                        // Using of automatic file checker
//                        showWarningDialog("Caution!", "Using of file checker may take a long time due to trying all possible options!");
//                        try {
//                            String fileFormat = FileChecker.CheckFormat(new FileInputStream(filename));
//                            switch (fileFormat) {
//                                case "ZIP":
//                                    showInformationDialogFormatChecker(fileFormat,
//                                            "I think the file '" + filename + "' may have the '.zip' format\n" +
//                                            "Try to use `ZipDearchiver` for reading");
//                                    break;
//                                case "RAR":
//                                    showInformationDialogFormatChecker(fileFormat,
//                                            "I think the file '" + filename + "' may have the '.rar' format\n" +
//                                                    "Try to use `RarDearchiver` for reading");
//                                    break;
//                                case "XML":
//                                    showInformationDialogFormatChecker(fileFormat,
//                                            "I think the file '" + filename + "' may have the '.xml' format\n" +
//                                                    "Try to use `XMLReader` for reading");
//                                    break;
//                                case "JSON":
//                                    showInformationDialogFormatChecker(fileFormat,
//                                            "I think the file '" + filename + "' may have the '.json' format\n" +
//                                                    "Try to use `JsonReader` for reading");
//                                    break;
//                                case "TXT":
//                                    showInformationDialogFormatChecker(fileFormat,
//                                            "I think the file '" + filename + "' may be encoded or have the '.txt' format\n" +
//                                                    "Try to use `Decoder` of 'TextFileReader' for reading");
//                                    break;
//                                case "Error!":
//                                default:
//                                    throw new IOException("File has unknown format or does not exist");
//                            }
//                        } catch (IOException exception) {
//                            showErrorDialog("Unexpected error while working with file",
//                                    "The file is damaged or does not exist\nThe file was not read");
//                        }
//                        break;
//        }
//        for (MathExpression expression : readExpressions) {
//            addRow(expression);
//        }
//        mathExpressions.addAll(readExpressions);
//    }
//
//
//
//    private void Write() {
//        RadioButton selectedRadioButton = (RadioButton) radioGroupWriting.getSelectedToggle();
//        String fileType = selectedRadioButton.getText();
//        selectedRadioButton = (RadioButton) radioGroupOptionsWriting.getSelectedToggle();
//        String option;
//        if (selectedRadioButton != null) {
//            option = selectedRadioButton.getText();
//        }
//        else {
//            option = null;
//        }
//        int calculatorType;
//        String filename = textFieldSelectCatWriting.getText();
//        String directoryPath;
//        switch (fileType) {
//            // Cases for different input file types
//            case "Write to txt":
//                // text file
//                try {
//                    calculatorType = showCalculatorChooser();
//                    if (calculatorType == 0) {
//                        break;
//                    }
//                    _FileWriter fileWriter = new _FileWriter(filename);
//                    fileWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
//                    fileWriter.CloseFile();
//                    showInformationDialogWriting();
//                } catch (IOException exception) {
//                    showErrorDialog("Error while working with text file",
//                            "Error while working text file\nThe file was not written");
//                }
//                break;
//            case "Write to json":
//                // JSON file
//                try {
//                    calculatorType = showCalculatorChooser();
//                    if (calculatorType == 0) {
//                        break;
//                    }
//                    if (Objects.equals(option, "Non API reader")) {
//                        JsonNonAPIWriter jsonNonAPIWriter = new JsonNonAPIWriter(filename);
//                        jsonNonAPIWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
//                        showInformationDialogWriting();
//                    } else {
//                        JsonWriter jsonWriter = new JsonWriter(filename);
//                        jsonWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
//                        showInformationDialogWriting();
//                    }
//                } catch (IOException exception) {
//                    showErrorDialog("Error while working with json file",
//                            "Error while working json file\nThe file was not written");
//                }
//                break;
//            case "Write to xml":
//                // XML file
//                try {
//                    calculatorType = showCalculatorChooser();
//                    if (calculatorType == 0) {
//                        break;
//                    }
//                    if (Objects.equals(option, "Non API reader")) {
//                        XMLNonAPIWriter xmlNonAPIWriter = new XMLNonAPIWriter(filename);
//                        xmlNonAPIWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
//                        showInformationDialogWriting();
//                    } else {
//                        XMLWriter xmlWriter = new XMLWriter(filename);
//                        xmlWriter.WriteListOfResultsOfMathExpressions(mathExpressions, calculatorType);
//                        showInformationDialogWriting();
//                    }
//                } catch (IOException exception) {
//                    showErrorDialog("Error while working with xml file",
//                            "Error while working xml file\nThe file was not written");
//                }
//                break;
//            case "Archive to zip file":
//                // ZIP archive
//                try {
//                    ArchiverZip archiverZip = new ArchiverZip(filename);
//                    directoryPath = showFileDirectoryChooser();
//                    archiverZip.Archive(ArchiverZip.makeListOfFilesToArchive(directoryPath));
//                    archiverZip.CloseArchiverZip();
//                    showInformationDialogArchiving(filename);
//                } catch (IOException exception) {
//                    showErrorDialog("Error while working with zip file",
//                            "Error while working zip file\nThe file was not archived");
//                }
//                break;
//            case "Archive to rar file":
//                // RAR archive
//                try {
//                    ArchiverRar archiverRar = new ArchiverRar(filename);
//                    directoryPath = showFileDirectoryChooser();
//                    archiverRar.Archive(directoryPath);
//                    showInformationDialogArchiving(filename);
//                } catch (IOException | InterruptedException exception) {
//                    showErrorDialog("Error while working with rar file",
//                            "Error while working rar file\nThe file was not archived");
//                }
//                break;
//            case "Encode file":
//                // Encoded file
//                showKeyInputDialog();
//                if (key == null) {
//                    break;
//                }
//                try {
//                    directoryPath = showFileChooser();
//                    Encoder encoder = new Encoder(directoryPath, filename);
//                    if (key.length() != 16) {
//                        throw new InvalidKeyException("Invalid key");
//                    }
//                    encoder.encryptFile(key);
//                    encoder.CloseEncoder();
//                    showInformationDialogEncoding(filename);
//                } catch (FileNotFoundException exception) {
//                    showErrorDialog("Error while working with encoded file",
//                            "The file was not found\nThe file was not encoded");
//                } catch (IOException | InvalidKeyException | InvalidParameterSpecException exception) {
//                    showErrorDialog("Error while working with encoded file",
//                            "Error while working encoded file\nThe file was not encoded");
//                }
//                break;
//        }
//    }
//
//    private void changeReaderOptions(String name) {
//        radioGroupOptionsReading.selectToggle(null);
//        switch (name) {
//            case "Read from txt":
//                radioListViewOptionsReading.setVisible(true);
//                radioListViewOptionsReading.prefHeightProperty().set(26);
//                labelOptionsReading.setVisible(true);
//                labelOptionsReading.prefHeightProperty().set(26);
//                radioListViewOptionsReading.getItems().clear();
//                radioListViewOptionsReading.getItems().add(new RadioButtonListItem("Buffered reader", radioGroupOptionsReading));
//                radioListViewOptionsReading.getItems().add(new RadioButtonListItem("Text reader", radioGroupOptionsReading));
//                break;
//            case "Read from json":
//            case "Read from xml":
//                radioListViewOptionsReading.setVisible(true);
//                radioListViewOptionsReading.prefHeightProperty().set(26);
//                labelOptionsReading.setVisible(true);
//                labelOptionsReading.prefHeightProperty().set(26);
//                radioListViewOptionsReading.getItems().clear();
//                radioListViewOptionsReading.getItems().add(new RadioButtonListItem("API reader", radioGroupOptionsReading));
//                radioListViewOptionsReading.getItems().add(new RadioButtonListItem("Non API reader", radioGroupOptionsReading));
//                break;
//            case "Dearchive zip file":
//            case "Dearchive rar file":
//            case "Decode file":
//            case "Check file format":
//                radioListViewOptionsReading.getItems().clear();
//                radioListViewOptionsReading.prefHeightProperty().set(0);
//                radioListViewOptionsReading.setVisible(false);
//                labelOptionsReading.setVisible(false);
//                labelOptionsReading.prefHeightProperty().set(0);
//                break;
//        }
//    }
//
//    @FXML
//    private Button writeButton;
//
//    private void updateWriteButton() {
//        writeButton.setDisable(radioGroupWriting.getSelectedToggle() == null ||
//                radioGroupOptionsWriting.getSelectedToggle() == null && !radioListViewOptionsWriting.getItems().isEmpty() ||
//                Objects.equals(textFieldSelectCatWriting.getText(), ""));
//    }
//
//    private void changeWriterOptions(String name) {
//        radioGroupOptionsWriting.selectToggle(null);
//        switch (name) {
//            case "Write to json":
//            case "Write to xml":
//                radioListViewOptionsWriting.setVisible(true);
//                radioListViewOptionsWriting.prefHeightProperty().set(26);
//                labelOptionsWriting.setVisible(true);
//                labelOptionsWriting.prefHeightProperty().set(26);
//                radioListViewOptionsWriting.getItems().clear();
//                radioListViewOptionsWriting.getItems().add(new RadioButtonListItem("API reader", radioGroupOptionsWriting));
//                radioListViewOptionsWriting.getItems().add(new RadioButtonListItem("Non API reader", radioGroupOptionsWriting));
//                break;
//            case "Write to txt":
//            case "Archive to zip file":
//            case "Archive to rar file":
//            case "Encode file":
//                radioListViewOptionsWriting.getItems().clear();
//                radioListViewOptionsWriting.prefHeightProperty().set(0);
//                radioListViewOptionsWriting.setVisible(false);
//                labelOptionsWriting.setVisible(false);
//                labelOptionsWriting.prefHeightProperty().set(0);
//                break;
//        }
//    }
//
//    @FXML
//    private void setTextFieldSelectCatWriting() {
//        textFieldSelectCatWriting.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateWriteButton();
//        });
//    }
//
//    @FXML
//    private void setTextFieldSelectCatReading() {
//        textFieldSelectCatReading.textProperty().addListener((observable, oldValue, newValue) -> {
//            updateReadButton();
//        });
//    }
//
//    @FXML
//    private void handleBtnSelectCatWriting(ActionEvent event) {
//        selectCat(btnSelectCatWriting, textFieldSelectCatWriting);
//    }
//
//    @FXML
//    private void handleBtnSelectCatReading(ActionEvent event) {
//        selectCat(btnSelectCatReading, textFieldSelectCatReading);
//    }
//
//    @FXML
//    void selectCat(Button btnSelectCat, TextField textFieldSelectCat) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialFileName("input.in");
//        fileChooser.setTitle("Opening");
//        File file = fileChooser.showOpenDialog(btnSelectCat.getScene().getWindow());
//        if (file != null) {
//            textFieldSelectCat.setText(file.getAbsolutePath());
//        }
//    }
//
//    public void cleanTemps() throws IOException {
//        for (DearchiverZip dearchiverZip : dearchiversZip) {
//            dearchiverZip.CloseDearchiverZip();
//        }
//        for (DearchiverRar dearchiverRar : dearchiversRar) {
//            dearchiverRar.CloseDearchiverRar();
//        }
//        for (Decoder decoder : decoders) {
//            decoder.closeDecoder();
//        }
//    }
//}