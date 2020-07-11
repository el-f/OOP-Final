package View;

import javafx.scene.control.TextField;

import java.util.Arrays;

public class FootballScoresForm extends ScoresForm {

    private TextField p1Score0, p1Score1;
    private TextField p2Score0, p2Score1;

    public FootballScoresForm(String _player1, String _player2) {
        super(_player1, _player2);

        p1Score0 = new TextField();
        p1Score1 = new TextField();
        p1ScoreFields.addAll(Arrays.asList(p1Score0, p1Score1));

        p2Score0 = new TextField();
        p2Score1 = new TextField();
        p2ScoreFields.addAll(Arrays.asList(p2Score0, p2Score1));

        buildVBoxForScoreFields();
    }
}
