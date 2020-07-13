package Controller;

import Model.Championship;
import Model.MyException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import View.*;

import java.util.concurrent.atomic.AtomicInteger;

import static Model.Championship.*;
import static Model.Championship.Stages.*;

public class Controller {
    private Championship championship;
    private View view;
    private View scoresView;
    private ScoresForm scoresForm;
    private BracketsView bracketsView;

    public Controller(Championship _championship, View _view) {
        championship = _championship;
        view = _view;
        scoresView = new View(new Stage(), "Game", 500, 500, false);
        PlayersForm playersForm = new PlayersForm(championship.getQuarterFinalists());
        view.updateBorderPane(playersForm.getBorderPane(), null);
        playersForm.addEventToSubmitButton(event -> {
            try {
                championship.addPlayer(playersForm.playerField.getText());
                playersForm.showPlayers(championship.getQuarterFinalists());
                view.updateBorderPane(playersForm.getBorderPane(), null);
            } catch (Exception exception) {
                alertForException(exception, view);
            }
        });
        playersForm.showPlayers(championship.getQuarterFinalists());

        bracketsView = new BracketsView();
        updateBracketsView(false);
        playersForm.addEventHandlerToStartButton(event -> {
            try {
                championship.checkQuartersReady();
                championship.setSport(Sports.valueOf(playersForm.getSport()));
                updateBracketsView(true);
            } catch (Exception exception) {
                alertForException(exception, view);
            }
        });

        for (AtomicInteger i = new AtomicInteger(0); i.get() < 4; i.getAndIncrement()) {
            bracketsView.addEventToButton(i.get(), event -> showScoresView(Quarters, i.get()));     //quarters buttons
        }
        bracketsView.addEventToButton(4, event -> showScoresView(Semis, 0));  //semis buttons
        bracketsView.addEventToButton(5, event -> showScoresView(Semis, 1));  //
        bracketsView.addEventToButton(6, event -> showScoresView(Finals, 0)); //finals button
    }

    private void showScoresView(Stages gameStage, int gamePosition) {
        initScoresView(gameStage, gamePosition, false, false);
        scoresForm.addEventToSubmitButton(eventForDoneBtn(gameStage, gamePosition, false));
    }

    private void initScoresView(Stages gameStage, int gamePosition, boolean overtime, boolean tennisOT) {
        String[] players = championship.getPlayersFromGamePosition(gamePosition, gameStage);
        scoresForm = getScoresForm(players[0], players[1], overtime);
        scoresView.updateBorderPane(scoresForm.getBorderPane(), championship.getSportName());
        scoresView.show();
        if (overtime)
            scoresForm.addEventToSubmitButton(eventForDoneBtn(gameStage, gamePosition, true));
        if (tennisOT)
            scoresForm.addEventToSubmitButton(eventForDoneBtn(gameStage, gamePosition, false));
    }

    private EventHandler<ActionEvent> eventForDoneBtn(Stages gameStage, int gamePosition, boolean overtime) {
        return event -> {
            try {
                playAndUpdate(gameStage, gamePosition, overtime);
                scoresView.close();
            } catch (Exception exception) {
                alertForException(exception, scoresView);
                if (exception.getMessage().equals("OVERTIME_NEEDED"))
                    initScoresView(gameStage, gamePosition, true, false);
                if (exception.getMessage().equals("TENNIS_OVERTIME_NEEDED"))
                    initScoresView(gameStage, gamePosition, false, true);
            }
        };
    }

    private void playAndUpdate(Stages gameStage, int gamePosition, boolean overtime) throws Exception {
        championship.play(
                gamePosition,
                gameStage,
                scoresForm.getScores(1), //scores for player 1
                scoresForm.getScores(2), //scores for player 2
                overtime);
        togglePlayBtn(gameStage, gamePosition, true);
        enableNextButton(gameStage, gamePosition);
        updateBracketsView(true);
    }

    private void enableNextButton(Stages gameStage, int gamePosition) {
        if (gameStage == Finals)
            return;
        int nextPos = gamePosition / 2;
        Stages nextStage = Stages.values()[gameStage.ordinal() + 1];
        if (championship.checkPosReady(nextPos, nextStage))
            togglePlayBtn(nextStage, nextPos, false);
    }

    //Transforms the gamePositions documented in Championship into a 0-6 index.
    //Toggles a play button from an array of [0-6] indexes.
    private void togglePlayBtn(Stages gameStage, int gamePosition, boolean disable) {
        bracketsView.toggleButtonDisabled(
                gameStage.equals(Quarters) ? gamePosition :
                        gameStage.equals(Semis) ? gamePosition + 4 :
                                gameStage.equals(Finals) ? gamePosition + 6 : -1, disable);
    }

    private void updateBracketsView(boolean show) {
        bracketsView.updateAll(
                championship.getQuarterFinalists(),
                championship.getSemiFinalists(),
                championship.getFinalists(),
                championship.getChampion());
        if (show)
            view.updateBorderPane(bracketsView.getBorderPane(), championship.getSportName());
    }

    private ScoresForm getScoresForm(String player1, String player2, boolean overtime) {
        if (overtime)
            return new ScoresForm(player1, player2, 1);
        switch (championship.getSport()) {
            case Basketball:
                return new ScoresForm(player1, player2, 4);
            case Tennis:
                return new ScoresForm(player1, player2, 5);
            case Football:
                return new ScoresForm(player1, player2, 2);
            default:    //Technically unreachable. In place of throwing UnexpectedException
                return new ScoresForm(null, null, -1);
        }
    }

    private void alertForException(Exception exception, View view) {
        String message;
        if (exception instanceof MyException)
            message = exception.getMessage();
        else {
            message = "Error! " + exception.getClass().getSimpleName();
        }
        view.showAlert(Alert.AlertType.ERROR, message);
    }
}
