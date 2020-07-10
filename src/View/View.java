package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class View {
    protected BorderPane borderPane;
    protected Alert alert;
    protected Scene scene;

    public View(Stage primaryStage) {
        alert = new Alert(Alert.AlertType.ERROR);
        borderPane = new BorderPane();


        scene = new Scene(borderPane, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Championship");
        primaryStage.show();
    }

    public void updateBorderPane(BorderPane _borderPane) {
        borderPane = _borderPane;
        HBox title = new HBox(new Text("Championship"));
        title.setAlignment(Pos.CENTER);
        borderPane.setTop(title);
        scene.setRoot(borderPane);
    }

    public static TextField buildTextFieldFromName(String name) {
        TextField textField = new TextField(name);
        textField.editableProperty().set(false);
        return textField;
    }

    public static VBox showPlayersFromList(List<String> players, int spacing) {
        VBox vBox = new VBox();
        players.forEach(x -> vBox.getChildren().add(buildTextFieldFromName(x)));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(spacing);
        vBox.setPadding(new Insets(0,0,0,15));
        return vBox;
    }

    public void showAlert(Alert.AlertType type, String message) {
        alert.setAlertType(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }


}
