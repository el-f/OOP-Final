package Model;

import java.util.List;

public class Game {

    protected String player1, player2;
    protected int rounds;

    public Game(String _player1, String _player2) {
        player1 = _player1;
        player2 = _player2;
    }

    public String playAndGetWinner(List<Integer> p1Scores, List<Integer> p2Scores) throws MyException {
        if (p1Scores.size() != rounds || p2Scores.size() != rounds)
            throw new MyException("Not All Rounds Played!");
        return playOvertimeAndGetWinner(
                p1Scores.stream().mapToInt(Integer::intValue).sum(),
                p2Scores.stream().mapToInt(Integer::intValue).sum());
    }

    protected String playOvertimeAndGetWinner(int p1Score, int p2Score) throws MyException {
        if (p1Score > p2Score)
            return player1;
        else if (p2Score > p1Score)
            return player2;
        else throw new MyException("Draw!");
    }

}
