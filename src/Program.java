import Controller.Controller;
import Model.Championship;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Championship championship = new Championship();
        View view = new View(primaryStage);
        Controller controller = new Controller(championship,view);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
