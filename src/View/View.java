package View;

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
    private BorderPane borderPane;
    private Alert alert;
    private Scene scene;
    private String title;
    private Stage primaryStage;

    public View(Stage _primaryStage, String _title, int width, int height, boolean show) {
        primaryStage = _primaryStage;
        alert = new Alert(Alert.AlertType.ERROR);
        borderPane = new BorderPane();
        title = _title;
        scene = new Scene(borderPane, width, height);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        if (show)
            primaryStage.show();
    }

    public void show() {
        primaryStage.show();
    }

    public void close() {
        primaryStage.close();
    }

    public void updateBorderPane(BorderPane _borderPane, String sport) {
        borderPane = _borderPane;
        String txt = title;
        txt += sport == null ? "" : " (" + sport + ")";
        HBox title = new HBox(new Text(txt));
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
        players.forEach(p -> vBox.getChildren().add(buildTextFieldFromName(p)));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(spacing);
        vBox.setPadding(new Insets(0, 0, 0, 15));
        return vBox;
    }

    public void showAlert(Alert.AlertType type, String message) {
        alert.setAlertType(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }


}
