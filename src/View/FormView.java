package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class FormView {
    public TextField playerField;
    private Button addPlayerBtn;
    private Button startBtn;
    private RadioButton tennis;
    private RadioButton basketBall;
    private RadioButton football;
    private BorderPane borderPane;

    public FormView(List<String> players) {
        borderPane = new BorderPane();
        // center
        playerField = new TextField();
        Text playerText = new Text("Participant name:");
        HBox playerBox = new HBox(playerText, playerField);
        playerBox.setSpacing(15);
        playerBox.setAlignment(Pos.CENTER);
        showPlayers(players);

        addPlayerBtn = new Button("Add participant");
        startBtn = new Button("Start Championship");
        HBox buttonsBox = new HBox(addPlayerBtn, startBtn);
        buttonsBox.setSpacing(15);
        buttonsBox.setAlignment(Pos.CENTER);
        VBox center = new VBox(playerBox, buttonsBox);
        center.setSpacing(35);
        center.setAlignment(Pos.CENTER);
        borderPane.setCenter(center);

        //right
        ToggleGroup toggleGroup = new ToggleGroup();
        tennis = new RadioButton("Tennis");
        basketBall = new RadioButton("BasketBall");
        football = new RadioButton("Football");
        tennis.setToggleGroup(toggleGroup);
        basketBall.setToggleGroup(toggleGroup);
        football.setToggleGroup(toggleGroup);
        tennis.setSelected(true);
        VBox type = new VBox(tennis, basketBall, football);
        type.setAlignment(Pos.CENTER_LEFT);
        type.setPadding(new Insets(0, 15, 0, 0));
        borderPane.setRight(type);
        showPlayers(players);
    }

    public void showPlayers(List<String> players) {
        //left
        borderPane.setLeft(View.showPlayersFromList(players, 10));
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public String getSport() {
        if (tennis.isSelected())
            return "Tennis";
        if (basketBall.isSelected())
            return "Basketball";
        if (football.isSelected())
            return "Football";
        return null;
    }

    public void addEventHandlerToAddButton(EventHandler<ActionEvent> addPlayerEventHandler) {
        addPlayerBtn.setOnAction(addPlayerEventHandler);
    }

    public void addEventHandlerToStartButton(EventHandler<ActionEvent> startEventHandler) {
        startBtn.setOnAction(startEventHandler);
    }
}
