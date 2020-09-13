package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public abstract class Form {
    protected final BorderPane borderPane;
    protected final Button submitButton;

    protected Form() {
        borderPane = new BorderPane();
        submitButton = new Button();
    }

    public void addEventHandlerToSubmitButton(EventHandler<ActionEvent> eventHandler) {
        submitButton.setOnAction(eventHandler);
        View.setCursorAsSelect(submitButton);
        borderPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
