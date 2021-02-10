import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FX extends Application {
    public static Stage primaryStage;

    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("first");
    }

    @Override
    public void start(Stage window) throws Exception {
        primaryStage = window;

        window.setTitle("ExpertHelp");
        window.setWidth(400);
        window.setHeight(500);

        window.setX(200);
        window.setY(100);



        Scene roleSelection = new Scene(FXMLLoader.load(getClass().getResource("RoleSelection.fxml")));
        window.setScene(roleSelection);
        window.show();
    }
}
