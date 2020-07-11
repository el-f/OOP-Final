package Model;

import java.util.Arrays;
import java.util.List;

public class Championship {
    private String[] quarterFinalists;
    private String[] semiFinalists;
    private String[] finalists;
    private String champion;

    public enum Stages {Groups, Quarters, Semis, Finals}
    private Stages currentStage;

    public enum Sports {Tennis, Football, Basketball}
    private Sports sport;

    public void setSport(Sports sport) {
        this.sport = sport;
    }

    public String getSport() {
        return sport.name();
    }

    public Championship() {
        quarterFinalists = new String[8];
        semiFinalists = new String[4];
        finalists = new String[2];
        currentStage = Stages.Groups;
    }

    public void advanceToNextStage() throws MyException {
        switch (currentStage) {
            case Groups:
                checkBeforeAdvance(quarterFinalists);
                break;
            case Quarters:
                checkBeforeAdvance(semiFinalists);
                break;
            case Semis:
                checkBeforeAdvance(finalists);
                break;
            default:
                throw new MyException("Can't Advance Anymore");
        }
    }

    private void checkBeforeAdvance(String[] arr) throws MyException {
        if (getNumOfItems(arr) == arr.length)
            currentStage = Stages.values()[currentStage.ordinal() + 1];
        else throw new MyException("Not All Spots Are Filled!");
    }

    public int getNumOfItems(String[] arr) {
        int count = 0;
        for (String s : arr) {
            if (s != null)
                count++;
        }
        return count;
    }

    public void addToArray(String item, String[] arr) throws MyException {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                arr[i] = item;
                return;
            }
        }
        throw new MyException("Player List Is Full!");
    }

    public void addPlayer(String player) throws MyException {
        for (String quarterFinalist : quarterFinalists) {
            if (quarterFinalist != null && quarterFinalist.equals(player))
                throw new MyException("Player Already In List!");
        }
        if (player.trim().isEmpty())
            throw new MyException("No Player Name!");
        addToArray(player, quarterFinalists);
    }


    public List<String> getQuarterFinalists() {
        return Arrays.asList(quarterFinalists);
    }

    public List<String> getSemiFinalists() {
        return Arrays.asList(semiFinalists);
    }

    public List<String> getFinalists() {
        return Arrays.asList(finalists);
    }

    public String getChampion() {
        return champion;
    }

    /*
        Game position in the stage:
        quarters: 4 games -> Positions: 0-3.
        semis: 2 games -> Positions: 0-1.
        finals: 1 positions.
        this way it's easier to manage what players play from inside this class.
     */
    public String[] getPlayersFromGamePosition(int gamePosition) throws MyException {
        int p1Index = gamePosition * 2, p2Index = p1Index + 1;
        String p1, p2;
        switch (currentStage) {
            case Quarters:
                p1 = quarterFinalists[p1Index];
                p2 = quarterFinalists[p2Index];
                break;
            case Semis:
                p1 = semiFinalists[p1Index];
                p2 = semiFinalists[p2Index];
                break;
            case Finals:
                p1 = finalists[p1Index];
                p2 = finalists[p2Index];
                break;
            default:
                throw new MyException("Unexpected Stage: " + currentStage);
        }
        return new String[]{p1, p2};
    }

    public void play(int gamePosition, List<Integer> p1Scores, List<Integer> p2Scores, boolean overtime) throws MyException {
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

        String winner = overtime ?
                game.playOvertimeAndGetWinner(sumScores(p1Scores), sumScores(p2Scores)) :
                game.playAndGetWinner(p1Scores, p2Scores);
        switch (currentStage) {
            case Quarters:
                addToArray(winner, semiFinalists);
                break;
            case Semis:
                addToArray(winner, finalists);
                break;
            case Finals:
                champion = winner;
                break;
            default:
                throw new MyException("Unexpected Stage: " + currentStage);
        }
        try {
            advanceToNextStage();
        } catch (Exception e){
            //not a problem. wait for all games.
        }
    }

    public static int sumScores(List<Integer> scores) {
        return scores.stream().mapToInt(Integer::intValue).sum();
    }

}
