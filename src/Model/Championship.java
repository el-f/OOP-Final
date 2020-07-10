package Model;

import java.util.Arrays;
import java.util.List;

public class Championship {

    List<String> quarterFinalists;
    List<String> semiFinalists;
    List<String> finalists;
    String champion;

    public enum Stages {Quarters, Semis, Finals}

    private Stages currentStage;

    private void advanceToNextStage() {
        currentStage = Stages.values()[currentStage.ordinal() + 1];
    }

    public enum Sports {Tennis, Football, Basketball}

    private Sports sport;

    public Championship() {
        quarterFinalists = Arrays.asList(new String[8]);    // \
        semiFinalists = Arrays.asList(new String[4]);       //  unmodifiable-size lists
        finalists = Arrays.asList(new String[2]);           // /
        currentStage = Stages.Quarters;
    }

    /*
        Game position in the stage:
        quarters: 4 games -> Positions: 1-4.
        semis: 2 games -> Positions: 1-2.
        finals: 1 positions.
        this way it's easier to manage what players play from inside this class.
     */
    private String[] getPlayersFromGamePosition(int gamePosition) throws MyException {
        int p1Index = gamePosition * 2, p2Index = p1Index + 1;
        String p1, p2;
        switch (currentStage) {
            case Quarters:
                p1 = quarterFinalists.get(p1Index);
                p2 = quarterFinalists.get(p2Index);
                break;
            case Semis:
                p1 = semiFinalists.get(p1Index);
                p2 = semiFinalists.get(p2Index);
                break;
            case Finals:
                p1 = finalists.get(p1Index);
                p2 = finalists.get(p2Index);
                break;
            default:
                throw new MyException("no stage");
        }
        return new String[]{p1, p2};
    }

    public String play(int gamePosition, List<Integer> p1Scores, List<Integer> p2Scores, boolean overtime) throws MyException {
        Game game;
        String[] players = getPlayersFromGamePosition(gamePosition);
        switch (sport) {
            case Tennis:
                game = new TennisGame(players[0], players[1]);
                break;
            case Football:
                game = new FootballGame(players[0], players[1]);
                break;
            case Basketball:
                game = new BasketballGame(players[0], players[1]);
                break;
            default:
                throw new MyException("Unexpected Game Type!");
        }
        return overtime ?
                game.playOvertimeAndGetWinner(Championship.sumScores(p1Scores), Championship.sumScores(p2Scores)) :
                game.playAndGetWinner(p1Scores, p2Scores);
    }

    public static int sumScores(List<Integer> scores) {
        return scores.stream().mapToInt(Integer::intValue).sum();
    }

}
