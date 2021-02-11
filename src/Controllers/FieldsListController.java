package Controllers;

import Client.UserProberties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FieldsListController extends GeneralController {
    @FXML
    private ImageView imageMedium;
    @FXML
    private Label nameLabel;
    @FXML
    private Label roleLabel;

    @FXML
    protected void initialize() {
            imageMedium.setImage(UserProberties.image);
            nameLabel.setText(UserProberties.name);
            roleLabel.setText(UserProberties.role);
    }

    @FXML
    private void pythonButtonClicked(ActionEvent event) throws Exception {
        UserProberties.field = "python";
        Stage s = getStageFromEvent(event);
        loadExpertsList(s);
    }

    @FXML
    private void javaButtonClicked(ActionEvent event) throws Exception {
        UserProberties.field = "java";
        loadExpertsList(getStageFromEvent(event));
    }

    @FXML
    private void jsButtonClicked(ActionEvent event) throws Exception {
        UserProberties.field = "js";
        loadExpertsList(getStageFromEvent(event));
    }

    @FXML
    private void csButtonClicked(ActionEvent event) throws Exception {
        UserProberties.field = "cs";
        loadExpertsList(getStageFromEvent(event));
    }


    private void loadExpertsList(Stage s) throws Exception {
        BorderPane expertsList = FXMLLoader.load(getClass().getResource("../FXML/ExpertsList.fxml"));
        s.getScene().setRoot(expertsList);
    }
}
