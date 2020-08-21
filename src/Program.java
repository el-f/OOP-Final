import Controller.Controller;
import Model.Championship;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage primaryStage) {
        new Controller(
                new Championship(),
                new View(primaryStage, "Championship", 1100, 650, true)
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
