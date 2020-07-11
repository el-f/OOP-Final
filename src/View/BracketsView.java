package View;

import javafx.geometry.Pos;
import javafx.scene.Node;
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

    public BracketsView() {
        borderPane = new BorderPane();

    }

    public void showAll(List<String> quarterFinalists, List<String> semiFinalists, List<String> finalists, String champion) {
        playQuarter0 = new Button("Play Game");
        playQuarter1 = new Button("Play Game");
        playQuarter2 = new Button("Play Game");
        playQuarter3 = new Button("Play Game");
        VBox quarterButtons = new VBox(playQuarter0, playQuarter1, playQuarter2, playQuarter3);
        quarterButtons.setAlignment(Pos.CENTER);
        quarterButtons.setSpacing(75);
        playSemi0 = new Button("Play Game");
        playSemi1 = new Button("Play Game");
        VBox semiButtons = new VBox(playSemi0, playSemi1);
        semiButtons.setAlignment(Pos.CENTER);
        semiButtons.setSpacing(180);
        playFinals = new Button("Play Game");
        VBox finalButton = new VBox(playFinals);
        finalButton.setAlignment(Pos.CENTER);
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
}
