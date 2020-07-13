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
                championship.setSport(Championship.Sports.valueOf(playersForm.getSport()));
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

    private void showScoresView(BracketsView bracketsView, Championship.Stages gameStage,
                                int gamePosition) {
        initScoresView(bracketsView, gameStage, gamePosition, false);
        scoresForm.addEventToSubmitButton(eventForDoneBtn(bracketsView, gameStage, gamePosition, false));
    }

    private void initScoresView(BracketsView bracketsView, Championship.Stages gameStage,
                                int gamePosition, boolean overtime) {
        String[] players = championship.getPlayersFromGamePosition(gamePosition, gameStage);
        scoresForm = getScoresForm(players[0], players[1], overtime);
        scoresView.updateBorderPane(scoresForm.getBorderPane(), championship.getSportName());
        scoresView.show();
        if (overtime)
            scoresForm.addEventToSubmitButton(eventForDoneBtn(bracketsView, gameStage, gamePosition, true));
    }

    private EventHandler<ActionEvent> eventForDoneBtn(BracketsView bracketsView, Championship.Stages gameStage,
                                                      int gamePosition, boolean overtime) {
        return event -> {
            try {
                playAndUpdate(bracketsView, gameStage, gamePosition, overtime);
                scoresView.close();
            } catch (Exception exception) {
                alertForException(exception, scoresView);
                if (exception.getMessage().equals("OVERTIME_NEEDED"))
                    initScoresView(bracketsView, gameStage, gamePosition, true);
            }
        };
    }

    private void playAndUpdate(BracketsView bracketsView, Championship.Stages gameStage,
                               int gamePosition, boolean overtime) throws Exception {
        championship.play(
                gamePosition,
                gameStage,
                scoresForm.getScores(1),
                scoresForm.getScores(2),
                overtime);
        bracketsView.toggleButtonDisabled(
                gameStage.equals(Quarters) ? gamePosition :
                        gameStage.equals(Semis) ? gamePosition + 4 :
                                gameStage.equals(Finals) ? gamePosition + 6 : -1, true);
        updateBracketsView(bracketsView); //for player list change
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
//        exception.printStackTrace(); //TODO: Delete
    }
}
