import Controller.Controller;
import Model.Championship;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

    @SuppressWarnings("unused") //controller is used
    @Override
    public void start(Stage primaryStage) {
        Championship championship = new Championship();
        View view = new View(primaryStage, "Championship", 1100, 700, true);
        Controller controller = new Controller(championship, view);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
