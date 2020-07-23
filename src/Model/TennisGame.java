package Model;

import java.util.List;

public class TennisGame extends Game {
    private final int alternativeRounds;
    public static final String drawMsg = "Draw! Playing Tennis Overtime...";

    public TennisGame(String _player1, String _player2) {
        super(_player1, _player2, 5);
        alternativeRounds = 3;
    }

    @Override
    public String playAndGetWinner(List<Integer> p1Scores, List<Integer> p2Scores) throws MyException {
        checkSizeEqual(p1Scores, p2Scores);
        if (p1Scores.size() != rounds && p1Scores.size() != alternativeRounds)
            throw new MyException("Not All Rounds Played!");
        int p1Wins = 0, p2Wins = 0, draws = 0;
        for (int i = 0; i < p1Scores.size(); i++) {
            if (p1Scores.get(i) > p2Scores.get(i))
                p1Wins++;
            else if (p2Scores.get(i) > p1Scores.get(i))
                p2Wins++;
            else draws++;
        }
        if (draws < 3) {
            if (p1Wins > p2Wins && p1Wins >= 3)
                return player1;
            if (p2Wins > p1Wins && p2Wins >= 3)
                return player2;
            if (p1Scores.size() == alternativeRounds)
                throw new MyException("Not a Margin of 3 Wins! Enter Scores For The Rest of The Rounds!");
        }
        throw new MyException(drawMsg);
    }

}
