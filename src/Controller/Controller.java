package Controller;

import Model.Championship;
import Model.MyException;
import View.View;
import View.FormView;
import javafx.scene.control.Alert;

public class Controller {
    Championship championship;
    View view;

    public Controller(Championship _championship, View _view) {
        championship = _championship;
        view = _view;
        FormView formView = new FormView(championship.getQuarterFinalists());
        view.updateBorderPane(formView.getBorderPane());
        formView.addEventHandlerToAddButton(event -> {
            try {
                championship.addPlayer(formView.playerField.getText());
                formView.showPlayers(championship.getQuarterFinalists());
                view.updateBorderPane(formView.getBorderPane());
            } catch (Exception exception) {
                alertForException(exception);
            }
        });
        formView.showPlayers(championship.getQuarterFinalists());
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
