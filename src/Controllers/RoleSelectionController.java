package Controllers;

import Controllers.GeneralController;
import Client.UserProberties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class RoleSelectionController extends GeneralController {
    @FXML
    protected void initialize() {
    }

    @FXML
    private void expertButtonClicked(ActionEvent event) throws Exception {
        UserProberties.role="Expert";
        loadInfoDialog(getStageFromEvent(event));
    }

    @FXML
    private void noviceButtonClicked(ActionEvent event) throws Exception {
        UserProberties.role="Novice";
        loadInfoDialog(getStageFromEvent(event));
    }

    private void loadInfoDialog(Stage s) {
        try {
            BorderPane infoDialog = FXMLLoader.load(getClass().getResource("../FXML/UserInfo.fxml"));
            s.getScene().setRoot(infoDialog);
        } catch (Exception e) {
            System.out.println("Error loading fxml");
        }
    }
}
