package Controllers;

import Client.UserProberties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FieldsListController extends GeneralController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label userRoleLabel;
    @FXML
    private ImageView image;


    @FXML
    protected void initialize() {
        nameLabel.setText(UserProberties.name);
        userRoleLabel.setText(UserProberties.role);
        image.setImage(UserProberties.image);
    }

    @FXML
    private void onJavaButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("java", getStageFromEvent(event));
    }

    @FXML
    private void onCsButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("cs", getStageFromEvent(event));
    }

    @FXML
    private void onJsButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("js", getStageFromEvent(event));
    }

    @FXML
    private void onHtmlButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("html", getStageFromEvent(event));
    }

    private void loadExpertsListWithField(String field, Stage window)  throws  Exception {
        UserProberties.field = field;
        load("ExpertsList", window);
    }

    @FXML
    private void onBackButtonClicked(ActionEvent event) throws Exception {
        load("UserInfo", getStageFromEvent(event));
    }
}
