package View;

import javafx.scene.control.TextField;

import java.util.Collections;

public class OvertimeForm extends ScoresForm {
    private TextField p1Score;
    private TextField p2Score;

    public OvertimeForm(String _player1, String _player2) {
        super(_player1, _player2);

        p1Score = new TextField();
        p1ScoreFields.addAll(Collections.singletonList(p1Score));

        p2Score = new TextField();
        p2ScoreFields.addAll(Collections.singletonList(p2Score));
    }
}
