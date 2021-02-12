package Controllers;

import Client.UserProberties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;

public class UserInfoController extends GeneralController {
    @FXML
    private ImageView image;
    @FXML
    private TextField nameTextField;
    private final String defaultImagePath = "src/images/user (4).png";
    private File imageFile;

    @FXML
    protected void initialize() {
        UserProberties.image = image.getImage();
    }

    @FXML
    private void onBrowseButtonClicked(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(getStageFromEvent(event));

        if (selectedFile != null) {
            imageFile = selectedFile;
            image.setImage(new Image(imageFile.toURI().toString()));
        }
    }

    @FXML
    private void onGoButtonClicked(ActionEvent event) throws Exception {
        UserProberties.name = nameTextField.getText();

        imageFile = imageFile != null ? imageFile : new File(defaultImagePath);

        UserProberties.image = new Image(imageFile.toURI().toString());

        byte[] fileContent = Files.readAllBytes(imageFile.toPath());
        UserProberties.encodedImage = Base64.getEncoder().encodeToString(fileContent);

        Stage s = getStageFromEvent(event);
        load("FieldsList", s);
    }

    @FXML
    private void onBackButtonClicked(ActionEvent event) throws Exception {

    }

    @FXML
    private void onLinkButtonClicked(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Image from link");
        dialog.setHeaderText("Get image from link");
        dialog.setContentText("Enter the link here:");

        Optional<String> link = dialog.showAndWait();
        if (link.isPresent()) {
            System.out.println(link.get());
        }
    }
}
