package Model;

import java.util.ArrayList;
import java.util.List;

public class Championship {

    List<String> players;

    public enum sports {Tennis, Football, Basketball}

    private sports sport;

    public void setSport(sports sport) {
        this.sport = sport;
    }

    public sports getSport() {
        return sport;
    }

    public Championship() {
        players = new ArrayList<>();
    }

    private String play(String player1, String player2) {

        return null;
    }

}
