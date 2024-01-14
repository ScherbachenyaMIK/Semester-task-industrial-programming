package UI;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class RadioButtonListItem extends HBox {

    private RadioButton radioButton;

    public RadioButtonListItem(String text, ToggleGroup toggleGroup) {
        radioButton = new RadioButton(text);
        radioButton.setToggleGroup(toggleGroup);
        getChildren().add(radioButton);
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }
}
