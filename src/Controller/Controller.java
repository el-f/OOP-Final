package Controller;

import Model.Championship;
import Model.MyException;
import View.View;
import View.PlayersForm;
import View.BracketsView;
import View.ScoresForm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


import static Model.Championship.*;
import static Model.Championship.Stages.*;

public class Controller {
    private Championship championship;
    private View view;
    private View scoresView;
    private ScoresForm scoresForm;

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


        BracketsView bracketsView = new BracketsView();
        bracketsView.showAll(
                championship.getQuarterFinalists(),
                championship.getSemiFinalists(),
                championship.getFinalists(),
                championship.getChampion());
        playersForm.addEventHandlerToStartButton(event -> {
            try {
                championship.checkQuartersReady();
                championship.setSport(Sports.valueOf(playersForm.getSport()));
                updateBracketsView(bracketsView);
            } catch (Exception exception) {
                alertForException(exception, view);
            }
        });

        bracketsView.addEventToBtnQ0(event -> showScoresView(bracketsView, Quarters, 0));
        bracketsView.addEventToBtnQ1(event -> showScoresView(bracketsView, Quarters, 1));
        bracketsView.addEventToBtnQ2(event -> showScoresView(bracketsView, Quarters, 2));
        bracketsView.addEventToBtnQ3(event -> showScoresView(bracketsView, Quarters, 3));
        bracketsView.addEventToBtnS0(event -> showScoresView(bracketsView, Semis, 0));
        bracketsView.addEventToBtnS1(event -> showScoresView(bracketsView, Semis, 1));
        bracketsView.addEventToBtnFinals(event -> showScoresView(bracketsView, Finals, 0));
    }

    private void showScoresView(BracketsView bracketsView, Stages gameStage,
                                int gamePosition) {
        initScoresView(bracketsView, gameStage, gamePosition, false, false);
        scoresForm.addEventToSubmitButton(eventForDoneBtn(bracketsView, gameStage, gamePosition, false));
    }

    private void initScoresView(BracketsView bracketsView, Stages gameStage,
                                int gamePosition, boolean overtime, boolean tennisOT) {
        String[] players = championship.getPlayersFromGamePosition(gamePosition, gameStage);
        scoresForm = getScoresForm(players[0], players[1], overtime);
        scoresView.updateBorderPane(scoresForm.getBorderPane(), championship.getSportName());
        scoresView.show();
        if (overtime)
            scoresForm.addEventToSubmitButton(eventForDoneBtn(bracketsView, gameStage, gamePosition, true));
        if (tennisOT)
            scoresForm.addEventToSubmitButton(eventForDoneBtn(bracketsView, gameStage, gamePosition, false));
    }

    private EventHandler<ActionEvent> eventForDoneBtn(BracketsView bracketsView, Stages gameStage,
                                                      int gamePosition, boolean overtime) {
        return event -> {
            try {
                playAndUpdate(bracketsView, gameStage, gamePosition, overtime);
                scoresView.close();
            } catch (Exception exception) {
                alertForException(exception, scoresView);
                if (exception.getMessage().equals("OVERTIME_NEEDED"))
                    initScoresView(bracketsView, gameStage, gamePosition, true, false);
                if (exception.getMessage().equals("TENNIS_OVERTIME_NEEDED"))
                    initScoresView(bracketsView, gameStage, gamePosition, false, true);

            }
        };
    }

    private void playAndUpdate(BracketsView bracketsView, Stages gameStage,
                               int gamePosition, boolean overtime) throws Exception {
        championship.play(
                gamePosition,
                gameStage,
                scoresForm.getScores(1),
                scoresForm.getScores(2),
                overtime);
        togglePlayBtn(bracketsView, gameStage, gamePosition, true);
        enableNextButton(bracketsView, gameStage, gamePosition);
        updateBracketsView(bracketsView); //for player list change
    }

    private void enableNextButton(BracketsView bracketsView, Stages stage, int gamePosition) {
        if (stage == Finals)
            return;
        int nextPos = (int) Math.floor(gamePosition / 2.0);
        Stages nextStage = Stages.values()[stage.ordinal() + 1];
        if (championship.checkPosReady(nextPos, nextStage))
            togglePlayBtn(bracketsView, nextStage, nextPos, false);
    }

    //Transforms the gamePositions documented in Championship into a 0-6 index.
    //Toggles a play button from an array of [0-6] indexes.
    private void togglePlayBtn(BracketsView bracketsView, Stages gameStage, int gamePosition, boolean disable) {
        bracketsView.toggleButtonDisabled(
                gameStage.equals(Quarters) ? gamePosition :
                        gameStage.equals(Semis) ? gamePosition + 4 :
                                gameStage.equals(Finals) ? gamePosition + 6 : -1, disable);
    }

    private void updateBracketsView(BracketsView bracketsView) {
        bracketsView.showAll(
                championship.getQuarterFinalists(),
                championship.getSemiFinalists(),
                championship.getFinalists(),
                championship.getChampion());
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
            default:
                return new ScoresForm(player1, player2, 0);
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
