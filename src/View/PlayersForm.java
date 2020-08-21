package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class PlayersForm extends Form {
    public final TextField playerField;
    private final Button startBtn;
    private final RadioButton tennisRB;
    private final RadioButton basketBallRB;
    private final RadioButton footballRB;

    public PlayersForm(List<String> players) {
        super();
        // center
        playerField = new TextField();
        Text playerText = new Text("Participant name:");
        HBox playerBox = new HBox(playerText, playerField);
        playerBox.setSpacing(15);
        playerBox.setAlignment(Pos.CENTER);
        showPlayers(players);

        submitButton.setText("Add participant");
        startBtn = new Button("Start Championship");
        HBox buttonsBox = new HBox(submitButton, startBtn);
        buttonsBox.setSpacing(15);
        buttonsBox.setAlignment(Pos.CENTER);
        VBox center = new VBox(playerBox, buttonsBox);
        center.setSpacing(35);
        center.setAlignment(Pos.CENTER);
        borderPane.setCenter(center);

        //right
        ToggleGroup toggleGroup = new ToggleGroup();
        tennisRB = new RadioButton("Tennis");
        basketBallRB = new RadioButton("BasketBall");
        footballRB = new RadioButton("Football");
        tennisRB.setToggleGroup(toggleGroup);
        basketBallRB.setToggleGroup(toggleGroup);
        footballRB.setToggleGroup(toggleGroup);
        tennisRB.setSelected(true);
        VBox type = new VBox(tennisRB, basketBallRB, footballRB);
        type.setAlignment(Pos.CENTER_LEFT);
        type.setPadding(new Insets(0, 25, 0, 0));
        borderPane.setRight(type);
        showPlayers(players);
        borderPane.setPadding(new Insets(25, 25, 25, 25));
    }

    public void showPlayers(List<String> players) {
        //left
        borderPane.setLeft(View.buildPlayersVBoxFromList(players, 15, Color.BLUE));
    }

    public String getSport() {
        if (tennisRB.isSelected())
            return "Tennis";
        if (basketBallRB.isSelected())
            return "Basketball";
        if (footballRB.isSelected())
            return "Football";
        return null;
    }

    public void addEventHandlerToStartButton(EventHandler<ActionEvent> startEventHandler) {
        startBtn.setOnAction(startEventHandler);
    }
}
