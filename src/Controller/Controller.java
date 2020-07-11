package Controller;

import Model.Championship;
import Model.MyException;
import View.View;
import View.PlayersForm;
import View.BracketsView;
import javafx.scene.control.Alert;

public class Controller {
    Championship championship;
    View view;

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
        playersForm.addEventHandlerToStartButton(event -> {
            try {
                championship.advanceToNextStage();
                championship.setSport(Championship.Sports.valueOf(playersForm.getSport()));
                bracketsView.showAll(
                        championship.getQuarterFinalists(),
                        championship.getSemiFinalists(),
                        championship.getFinalists(),
                        championship.getChampion());
                view.updateBorderPane(bracketsView.getBorderPane(), championship.getSport());
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        bracketsView.addEventHandlerToPlayBtnQ0(event -> {

        });
        bracketsView.addEventHandlerToPlayBtnQ1(event -> {

        });
        bracketsView.addEventHandlerToPlayBtnQ2(event -> {

        });
        bracketsView.addEventHandlerToPlayBtnQ3(event -> {

        });
        bracketsView.addEventHandlerToPlayBtnS0(event -> {

        });
        bracketsView.addEventHandlerToPlayBtnS1(event -> {

        });
        bracketsView.addEventHandlerToPlayBtnF(event -> {

        });
    }

    private void alertForException(Exception exception) {
        String message;
        if (exception instanceof MyException)
            message = exception.getMessage();
        else {
            message = "Error! " + exception.getClass().getSimpleName();
        }
        view.showAlert(Alert.AlertType.ERROR, message);
    }
}
