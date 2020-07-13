package View;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;

public class ScoresForm extends Form {

    protected List<TextField> p1ScoreFields;
    protected List<TextField> p2ScoreFields;
    protected Text player1, player2;

    public ScoresForm(String _player1, String _player2, int rounds) {
        super();
        player1 = new Text(_player1 + ": ");
        player2 = new Text(_player2 + ": ");
        submitButton.setText("Done");
        p1ScoreFields = new ArrayList<>();
        p2ScoreFields = new ArrayList<>();
        for (int i = 0; i < rounds; i++) {
            p1ScoreFields.add(new TextField());
            p2ScoreFields.add(new TextField());
        }
        buildVBoxForScoreFields();
    }

    public List<Integer> getScores(int player) throws UnexpectedException {
        List<TextField> textFieldList;
        if (player == 1)
            textFieldList = p1ScoreFields;
        else if (player == 2)
            textFieldList = p2ScoreFields;
        else throw new UnexpectedException("unexpected value: " + player);
        List<Integer> integerList = new ArrayList<>();
        textFieldList.forEach(textField -> {
            int score;
            if (!textField.getText().trim().isEmpty()) {
                score = Integer.parseInt(textField.getText());
                integerList.add(score);
            }
        });
        return integerList;
    }

    protected void buildVBoxForScoreFields() {
        VBox all = new VBox(
                buildHBoxForScoreField(player1, p1ScoreFields),
                buildHBoxForScoreField(player2, p2ScoreFields),
                submitButton);
        all.setSpacing(25);
        all.setAlignment(Pos.CENTER);
        borderPane.setCenter(all);
    }

    private HBox buildHBoxForScoreField(Text player, List<TextField> scoreFields) {
        HBox hBox = new HBox(player);
        scoreFields.forEach(sf -> {
            sf.setMaxWidth(50);
            hBox.getChildren().add(sf);
        });
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }


}
