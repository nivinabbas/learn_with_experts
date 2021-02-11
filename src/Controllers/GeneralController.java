package Controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class GeneralController {
    protected Stage getStageFromEvent(ActionEvent event) {
        return (Stage) ((Node)event.getSource()).getScene().getWindow();
    }
}
