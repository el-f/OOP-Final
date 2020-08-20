package Controller;

import Model.Championship;
import Model.Game;
import Model.MyException;
import Model.TennisGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import View.*;

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
        scoresView = new View(new Stage(), "Game", 500, 300, false);
        PlayersForm playersForm = new PlayersForm(championship.getQuarterFinalists());
        view.updateBorderPane(playersForm.getBorderPane(), null);
        playersForm.addEventHandlerToSubmitButton(event -> {
            try {
                championship.addPlayer(playersForm.playerField.getText());
                playersForm.showPlayers(championship.getQuarterFinalists());
                view.updateBorderPane(playersForm.getBorderPane(), null);
                validatePlayer(playersForm.playerField.getText());
                playersForm.playerField.setText("");
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

        //quarters buttons
        bracketsView.addEventHandlerToButton(0, event -> initScoresView(Quarters, 0, false));
        bracketsView.addEventHandlerToButton(1, event -> initScoresView(Quarters, 1, false));
        bracketsView.addEventHandlerToButton(2, event -> initScoresView(Quarters, 2, false));
        bracketsView.addEventHandlerToButton(3, event -> initScoresView(Quarters, 3, false));
        //semis buttons
        bracketsView.addEventHandlerToButton(4, event -> initScoresView(Semis, 0, false));
        bracketsView.addEventHandlerToButton(5, event -> initScoresView(Semis, 1, false));
        //finals button
        bracketsView.addEventHandlerToButton(6, event -> initScoresView(Finals, 0, false));
    }

    private void initScoresView(Stages gameStage, int gamePosition, boolean overtime) {
        String[] players = championship.getPlayersFromGamePosition(gamePosition, gameStage);
        scoresForm = getScoresForm(players[0], players[1], overtime);
        scoresView.updateBorderPane(scoresForm.getBorderPane(), championship.getSportName());
        scoresForm.addEventHandlerToSubmitButton(eventForDoneBtn(gameStage, gamePosition, overtime));
        scoresView.show();
    }

    private EventHandler<ActionEvent> eventForDoneBtn(Stages gameStage, int gamePosition, boolean overtime) {
        return event -> {
            try {
                playAndUpdate(gameStage, gamePosition, overtime);
                scoresView.close();
            } catch (Exception exception) {
                alertForException(exception, scoresView);
                if (exception.getMessage().equals(Game.drawMsg))
                    initScoresView(gameStage, gamePosition, true);
                else if (exception.getMessage().equals(TennisGame.drawMsg))
                    scoresForm.clearTextFields();
            }
        };
    }

    private void playAndUpdate(Stages gameStage, int gamePosition, boolean overtime) throws Exception {
        championship.play(
                gamePosition,
                gameStage,
                scoresForm.getScores(1), //scores for player 1
                scoresForm.getScores(2), //scores for player 2
                overtime
        );
        togglePlayBtn(gameStage, gamePosition, true);
        enableNextButton(gameStage, gamePosition);
        updateBracketsView(true);
    }

    private void enableNextButton(Stages gameStage, int gamePosition) {
        if (gameStage == Finals)
            return;
        int nextPos = gamePosition / 2;
        Stages nextStage = Stages.values()[gameStage.ordinal() + 1];
        if (championship.checkGameReady(nextPos, nextStage))
            togglePlayBtn(nextStage, nextPos, false);
    }

    //Transforms the gamePositions as they're documented in Championship into a 0-6 index.
    //Toggles a play button from an array of [0-6] indexes.
    //if disable is TRUE then the disable that button.
    private void togglePlayBtn(Stages gameStage, int gamePosition, boolean disable) {
        bracketsView.toggleButtonDisabled(
                gameStage.equals(Quarters) ? gamePosition :
                        gameStage.equals(Semis) ? gamePosition + 4 :
                                gameStage.equals(Finals) ? gamePosition + 6 : -1, disable
        );
    }

    private void updateBracketsView(boolean show) {
        String champion = championship.getChampion();
        if (champion != null)
            view.showAlert(Alert.AlertType.INFORMATION, "The Champion is: " + champion + "!");
        bracketsView.updateAll(
                championship.getQuarterFinalists(),
                championship.getSemiFinalists(),
                championship.getFinalists(),
                champion
        );
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

    private void validatePlayer(String player) {
        if (player.toLowerCase().matches("elazar|fine|elazar[\\s]+fine"))
            view.updateBorderPane(
                    new BorderPane(new Text(player + " is the winner by default!\tGG WP")),
                    null
            );
    }

    private void alertForException(Exception exception, View view) {
        String message;
        if (exception instanceof MyException)
            message = exception.getMessage();
        else
            message = "Error! " + exception.getClass().getSimpleName();
        view.showAlert(Alert.AlertType.ERROR, message);
    }
}
