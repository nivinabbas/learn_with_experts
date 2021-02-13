package Controllers;

import Client.UserProberties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class FieldsListController extends GeneralController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label userRoleLabel;
    @FXML
    private Ellipse userImageEllipse;


    @FXML
    protected void initialize() {
        nameLabel.setText(UserProberties.name);
        userRoleLabel.setText(UserProberties.role);
        userImageEllipse.setFill(new ImagePattern(UserProberties.image));
        userImageEllipse.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
    }

    @FXML
    private void onJavaButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("Java", getStageFromEvent(event));
    }

    @FXML
    private void onCsButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("C Sharp", getStageFromEvent(event));
    }

    @FXML
    private void onPythonButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("Python", getStageFromEvent(event));
    }

    @FXML
    private void onJsButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("JavaScript", getStageFromEvent(event));
    }

    @FXML
    private void onNodeButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("Node js", getStageFromEvent(event));
    }

    @FXML
    private void onMongoButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("MongoDB", getStageFromEvent(event));
    }

    @FXML
    private void onReactButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("ReactJS", getStageFromEvent(event));
    }

    @FXML
    private void onVueButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("VueJS", getStageFromEvent(event));
    }


    @FXML
    private void onHtmlButtonClicked(ActionEvent event) throws Exception {
        loadExpertsListWithField("html", getStageFromEvent(event));
    }

    private void loadExpertsListWithField(String field, Stage window) throws Exception {
        UserProberties.field = field;
        load("ExpertsList", window);
    }

    @FXML
    private void onBackButtonClicked(ActionEvent event) throws Exception {
        load("UserInfo", getStageFromEvent(event));
    }
}
