package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BracketsView {
    private BorderPane borderPane;
    private Button playQuarter0, playQuarter1, playQuarter2, playQuarter3;
    private Button playSemi0, playSemi1;
    private Button playFinals;
    private boolean[] disableProperties;

    public BracketsView() {
        borderPane = new BorderPane();
        disableProperties = new boolean[]{false, false, false, false, true, true, true};
        playQuarter0 = new Button("Play Game");
        playQuarter1 = new Button("Play Game");
        playQuarter2 = new Button("Play Game");
        playQuarter3 = new Button("Play Game");

        playSemi0 = new Button("Play Game");
        playSemi1 = new Button("Play Game");

        playFinals = new Button("Play Game");
    }

    public void updateAll(List<String> quarterFinalists, List<String> semiFinalists, List<String> finalists, String champion) {

        VBox quarterButtons = new VBox(playQuarter0, playQuarter1, playQuarter2, playQuarter3);
        quarterButtons.setAlignment(Pos.CENTER);
        quarterButtons.setSpacing(75);

        VBox semiButtons = new VBox(playSemi0, playSemi1);
        semiButtons.setAlignment(Pos.CENTER);
        semiButtons.setSpacing(180);

        VBox finalButton = new VBox(playFinals);
        finalButton.setAlignment(Pos.CENTER);
        updateDisableProperties();
        HBox all = new HBox(
                View.showPlayersFromList(quarterFinalists, 25),
                quarterButtons,
                View.showPlayersFromList(semiFinalists, 75),
                semiButtons,
                View.showPlayersFromList(finalists, 180),
                finalButton,
                View.showPlayersFromList(Collections.singletonList(champion), 0));
        all.setAlignment(Pos.CENTER);
        all.setSpacing(20);
        borderPane.setCenter(all);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void addEventToBtnQ0(EventHandler<ActionEvent> eventHandler) {
        playQuarter0.setOnAction(eventHandler);
    }

    public void addEventToBtnQ1(EventHandler<ActionEvent> eventHandler) {
        playQuarter1.setOnAction(eventHandler);
    }

    public void addEventToBtnQ2(EventHandler<ActionEvent> eventHandler) {
        playQuarter2.setOnAction(eventHandler);
    }

    public void addEventToBtnQ3(EventHandler<ActionEvent> eventHandler) {
        playQuarter3.setOnAction(eventHandler);
    }

    public void addEventToBtnS0(EventHandler<ActionEvent> eventHandler) {
        playSemi0.setOnAction(eventHandler);
    }

    public void addEventToBtnS1(EventHandler<ActionEvent> eventHandler) {
        playSemi1.setOnAction(eventHandler);
    }

    public void addEventToBtnFinals(EventHandler<ActionEvent> eventHandler) {
        playFinals.setOnAction(eventHandler);
    }

    public void toggleButtonDisabled(int buttonPosition, boolean disable) {
        disableProperties[buttonPosition] = disable;
    }

    private void updateDisableProperties() {
        List<Button> buttons = Arrays.asList(playQuarter0, playQuarter1, playQuarter2, playQuarter3,
                playSemi0, playSemi1,
                playFinals);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setDisable(disableProperties[i]);
        }
    }
}
