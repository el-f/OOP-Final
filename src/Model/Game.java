package Model;

import java.util.List;

public class Game {

    protected String player1, player2;
    protected int rounds;
    public static final String drawMsg = "Draw! Playing Overtime...";

    public Game(String _player1, String _player2, int _rounds) {
        player1 = _player1;
        player2 = _player2;
        rounds = _rounds;
    }

    String playAndGetWinner(List<Integer> p1Scores, List<Integer> p2Scores) throws MyException {
        checkSizeEqual(p1Scores, p2Scores);
        if (p1Scores.size() != rounds)
            throw new MyException("Not All Rounds Played!");
        return playOvertimeAndGetWinner(Championship.sumScores(p1Scores), Championship.sumScores(p2Scores));
    }

    protected void checkSizeEqual(List<Integer> p1Scores, List<Integer> p2Scores) throws MyException {
        if (p1Scores.size() != p2Scores.size())
            throw new MyException("Invalid Scores Input!");
    }

    protected String playOvertimeAndGetWinner(int p1Score, int p2Score) throws MyException {
        if (p1Score > p2Score)
            return player1;
        else if (p2Score > p1Score)
            return player2;
        else throw new MyException(drawMsg);
    }

}
