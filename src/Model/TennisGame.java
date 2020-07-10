package Model;

import java.util.List;

public class TennisGame extends Game {
    public TennisGame(String _player1, String _player2) {
        super(_player1, _player2);
        rounds = 5;
    }

    @Override
    public String playAndGetWinner(List<Integer> p1Scores, List<Integer> p2Scores) throws MyException {
        if (p1Scores.size() != rounds || p2Scores.size() != rounds)
            throw new MyException("Not All Rounds Played!");
        return playOvertimeAndGetWinner(
                p1Scores.stream().mapToInt(Integer::intValue).sum(),
                p2Scores.stream().mapToInt(Integer::intValue).sum());
    }

}
