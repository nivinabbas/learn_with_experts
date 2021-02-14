import Client.ClientNetwork;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;

public class MainApp extends Application {
    public static Stage primaryStage;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage window) throws Exception {
        primaryStage = window;

        window.setTitle("Learn With Experts");
        window.setWidth(406);
        window.setHeight(630);
        window.setResizable(false);
        window.setX(200);
        window.setY(100);
        Scene roleSelection = new Scene(FXMLLoader.load(getClass().getResource("FXML/RoleSelection.fxml")));

        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    ClientNetwork.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        window.setScene(roleSelection);
        window.show();
    }
}
