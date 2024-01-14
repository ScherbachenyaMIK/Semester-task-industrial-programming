package UI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableRowData {

    private final StringProperty mathExpression;
    private final StringProperty regexResult;
    private final StringProperty rpnResult;
    private final StringProperty apiResult;

    public TableRowData(String mathExpression, String regexResult, String rpnResult, String apiResult) {
        this.mathExpression = new SimpleStringProperty(mathExpression);
        this.regexResult = new SimpleStringProperty(regexResult);
        this.rpnResult = new SimpleStringProperty(rpnResult);
        this.apiResult = new SimpleStringProperty(apiResult);
    }

    public StringProperty mathExpressionProperty() {
        return mathExpression;
    }

    public StringProperty regexResultProperty() {
        return regexResult;
    }

    public StringProperty rpnResultProperty() {
        return rpnResult;
    }

    public StringProperty apiResultProperty() {
        return apiResult;
    }
}
