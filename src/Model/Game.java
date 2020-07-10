package Model;

import java.util.List;

public abstract class Game {

    protected String player1, player2;
    protected int rounds;

    public Game(String _player1, String _player2) {
        player1 = _player1;
        player2 = _player2;
    }

    public String playAndGetWinner(List<Integer> p1Scores, List<Integer> p2Scores) throws MyException {
        if (p1Scores.size() != p2Scores.size())
            throw new MyException("uneven size of rounds lists");
        if (p1Scores.size() != rounds)
            throw new MyException("Not All Rounds Played!");
        return playOvertimeAndGetWinner(Championship.sumScores(p1Scores), Championship.sumScores(p2Scores));
    }

    protected String playOvertimeAndGetWinner(int p1Score, int p2Score) throws MyException {
        if (p1Score > p2Score)
            return player1;
        else if (p2Score > p1Score)
            return player2;
        else throw new MyException("Draw!");
    }

}
