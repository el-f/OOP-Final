package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class View {
    private BorderPane borderPane;
    private final Alert alert;
    private final Scene scene;
    private final String title;
    private final Stage stage;

    public View(Stage _stage, String _title, int width, int height, boolean show) {
        stage = _stage;
        alert = new Alert(Alert.AlertType.ERROR);
        borderPane = new BorderPane();
        title = _title;
        scene = new Scene(borderPane, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        if (show) stage.show();
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public void updateBorderPane(BorderPane _borderPane, String sport) {
        borderPane = _borderPane;
        String txt = sport == null ? "" : sport + " ";
        txt += title;
        Text text = new Text(txt);
        text.setFont(Font.font("Tahoma Bold", FontWeight.BOLD, 25));
        text.setFill(Color.GOLDENROD);
        text.setStroke(Color.BLACK);
        text.setFontSmoothingType(FontSmoothingType.LCD);
        HBox title = new HBox(text);
        title.setPadding(new Insets(40,0,0,0));
        title.setAlignment(Pos.CENTER);
        borderPane.setTop(title);
        scene.setRoot(borderPane);
    }

    private static TextField buildTextFieldFromName(String name, Color color) {
        TextField textField = new TextField(name);
        textField.setEditable(false);
        DropShadow shadow = new DropShadow();
        shadow.setColor(color);
        shadow.setWidth(15);
        shadow.setHeight(15);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        textField.setEffect(shadow);
        return textField;
    }

    static VBox buildPlayersVBoxFromList(List<String> players, int spacing, Color color) {
        VBox vBox = new VBox();
        players.forEach(p -> vBox.getChildren().add(buildTextFieldFromName(p, color)));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(spacing);
        vBox.setPadding(new Insets(0, 0, 0, 15));
        return vBox;
    }

    static void setCursorAsSelect(Region object) {
        object.setCursor(Cursor.HAND);
    }

    public void showAlert(Alert.AlertType type, String message) {
        alert.setAlertType(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }


}
