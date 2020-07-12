package Controller;

import Model.Championship;
import Model.MyException;
import View.View;
import View.PlayersForm;
import View.BracketsView;
import View.ScoresForm;
import View.BasketballScoresForm;
import View.FootballScoresForm;
import View.TennisScoresForm;
import View.OvertimeForm;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static Model.Championship.Stages.*;

public class Controller {
    private Championship championship;
    private View view;

    public Controller(Championship _championship, View _view) {
        championship = _championship;
        view = _view;
        PlayersForm playersForm = new PlayersForm(championship.getQuarterFinalists());
        view.updateBorderPane(playersForm.getBorderPane(), null);
        playersForm.addEventToSubmitButton(event -> {
            try {
                championship.addPlayer(playersForm.playerField.getText());
                playersForm.showPlayers(championship.getQuarterFinalists());
                view.updateBorderPane(playersForm.getBorderPane(), null);
            } catch (Exception exception) {
                alertForException(exception);
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
                alertForException(exception);
            }
        });

        bracketsView.addEventToBtnQ0(event -> showScoresForm(bracketsView, Quarters, 0, false));
        bracketsView.addEventToBtnQ1(event -> showScoresForm(bracketsView, Quarters, 1, false));
        bracketsView.addEventToBtnQ2(event -> showScoresForm(bracketsView, Quarters, 2, false));
        bracketsView.addEventToBtnQ3(event -> showScoresForm(bracketsView, Quarters, 3, false));
        bracketsView.addEventToBtnS0(event -> showScoresForm(bracketsView, Semis, 0, false));
        bracketsView.addEventToBtnS1(event -> showScoresForm(bracketsView, Semis, 1, false));
        bracketsView.addEventToBtnFinals(event -> showScoresForm(bracketsView, Finals, 0, false));
    }

    private void showScoresForm(BracketsView bracketsView, Championship.Stages gameStage,
                                int gamePosition, boolean overtime) {
        Stage stage = new Stage();
        String[] players = championship.getPlayersFromGamePosition(gamePosition, gameStage);
        ScoresForm scoresForm = getScoresForm(players[0], players[1], overtime);
        Scene scene = new Scene(Objects.requireNonNull(scoresForm).getBorderPane(), 500, 500);
        stage.setScene(scene);
        stage.show();
        scoresForm.addEventToSubmitButton(event -> {
            try {
                championship.play(gamePosition,
                        gameStage,
                        scoresForm.getScores(1),
                        scoresForm.getScores(2),
                        overtime);
                bracketsView.toggleButtonDisabled(
                        gameStage.equals(Quarters) ? gamePosition :
                                gameStage.equals(Semis) ? gamePosition + 4 :
                                        gameStage.equals(Finals) ? gamePosition + 6 : -1, true);
                updateBracketsView(bracketsView); //for player list change
                stage.close();
            } catch (Exception exception) {
                if (exception.getMessage().equals("OVERTIME_NEEDED")) {
                    stage.close();
                    showScoresForm(bracketsView, gameStage, gamePosition, true);
                }
                alertForException(exception);
            }
        });
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
            return new OvertimeForm(player1, player2);
        switch (championship.getSport()) {
            case Basketball:
                return new BasketballScoresForm(player1, player2);
            case Tennis:
                return new TennisScoresForm(player1, player2);
            case Football:
                return new FootballScoresForm(player1, player2);
        }
        return null;
    }

    private void alertForException(Exception exception) {
        String message;
        if (exception instanceof MyException)
            message = exception.getMessage();
        else {
            message = "Error! " + exception.getClass().getSimpleName();
        }
        view.showAlert(Alert.AlertType.ERROR, message);
        exception.printStackTrace(); //TODO: Delete
    }
}
