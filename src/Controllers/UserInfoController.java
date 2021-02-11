package Controllers;

import Client.UserProberties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.Optional;

public class UserInfoController extends GeneralController {
    @FXML
    private ImageView imageBig;
    @FXML
    private TextField nameInput;

    @FXML
    protected void initialize() {
        UserProberties.image = imageBig.getImage();
    }

    @FXML
    private void browseButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(getStageFromEvent(event));
        if (selectedFile != null) {
            UserProberties.image = new Image(selectedFile.toURI().toString());
            imageBig.setImage(UserProberties.image);
        }
    }

    @FXML
    private void goButtonClicked(ActionEvent event) throws Exception {
        UserProberties.name = nameInput.getText();
        Stage s = getStageFromEvent(event);
        loadFieldsList(s);
    }

    @FXML
    private void fromLinkButtonClicked(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Image from link");
        dialog.setHeaderText("Get image from link");
        dialog.setContentText("Enter the link here:");

        Optional<String> link = dialog.showAndWait();
        if (link.isPresent()) {
            System.out.println(link.get());
        }
    }

    private void loadFieldsList(Stage s) throws Exception {
        BorderPane fieldsList = FXMLLoader.load(getClass().getResource("../FXML/FieldsList.fxml"));
        s.getScene().setRoot(fieldsList);
    }
}
