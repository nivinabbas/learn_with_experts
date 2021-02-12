package Controllers;

import Client.UserProberties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class RoleSelectionController extends GeneralController {
    @FXML
    protected void initialize() {
    }

    @FXML
    private void onExpertButtonClicked(ActionEvent event) throws Exception {
        UserProberties.role="Expert";
        loadInfoDialog(getStageFromEvent(event));
    }

    @FXML
    private void onNoviceButtonClicked(ActionEvent event) throws Exception {
        UserProberties.role="Novice";
        loadInfoDialog(getStageFromEvent(event));
    }

    private void loadInfoDialog(Stage s) {
        try {
            AnchorPane infoDialog = FXMLLoader.load(getClass().getResource("../FXML/UserInfo.fxml"));
            s.getScene().setRoot(infoDialog);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading fxml");
        }
    }


}
