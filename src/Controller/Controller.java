package Controller;

import Model.Championship;
import Model.MyException;
import View.View;
import View.FormView;
import View.BracketsView;
import javafx.scene.control.Alert;

public class Controller {
    Championship championship;
    View view;

    public Controller(Championship _championship, View _view) {
        championship = _championship;
        view = _view;
        FormView formView = new FormView(championship.getQuarterFinalists());
        view.updateBorderPane(formView.getBorderPane(), null);
        formView.addEventHandlerToAddButton(event -> {
            try {
                championship.addPlayer(formView.playerField.getText());
                formView.showPlayers(championship.getQuarterFinalists());
                view.updateBorderPane(formView.getBorderPane(), null);
            } catch (Exception exception) {
                alertForException(exception);
            }
        });
        formView.showPlayers(championship.getQuarterFinalists());

        BracketsView bracketsView = new BracketsView();
        formView.addEventHandlerToStartButton(event -> {
            try {
                championship.advanceToNextStage();
                championship.setSport(Championship.Sports.valueOf(formView.getSport()));
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
