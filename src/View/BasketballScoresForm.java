package View;

import javafx.scene.control.TextField;

import java.util.Arrays;

public class BasketballScoresForm extends ScoresForm {

    private TextField p1Score0, p1Score1, p1Score2, p1Score3;
    private TextField p2Score0, p2Score1, p2Score2, p2Score3;

    public BasketballScoresForm(String _player1, String _player2) {
        super(_player1, _player2);
        p1Score0 = new TextField();
        p1Score1 = new TextField();
        p1Score2 = new TextField();
        p1Score3 = new TextField();
        p1ScoreFields.addAll(Arrays.asList(p1Score0, p1Score1, p1Score2, p1Score3));

        p2Score0 = new TextField();
        p2Score1 = new TextField();
        p2Score2 = new TextField();
        p2Score3 = new TextField();
        p2ScoreFields.addAll(Arrays.asList(p2Score0, p2Score1, p2Score2, p2Score3));
    }
}
