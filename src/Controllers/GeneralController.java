package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GeneralController {
    protected Stage getStageFromEvent(ActionEvent event) {
        return (Stage) ((Node)event.getSource()).getScene().getWindow();
    }

    protected void load(String filename, Stage s) throws Exception {
        AnchorPane parent = FXMLLoader.load(getClass().getResource("../FXML/"+filename+".fxml"));
        s.getScene().setRoot(parent);
    }
}
