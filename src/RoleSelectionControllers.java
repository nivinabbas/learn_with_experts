import com.sun.javafx.scene.control.InputField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class RoleSelectionControllers {
    @FXML
    private ImageView imageBig;
    @FXML
    private ImageView imageMedium;
    @FXML
    private TextField nameInput;
    @FXML
    private Label nameLabel;
    @FXML
    private Label roleLabel;

    private static String role;
    private static String name;
    private static Image image;

    @FXML
    protected void initialize() {
        if (imageMedium != null) {
            imageMedium.setImage(image);
            nameLabel.setText(name);
            roleLabel.setText(role);
        } else if (imageBig != null) {
            image = imageBig.getImage();
        }
    }

    @FXML
    private void expertButtonClicked(ActionEvent event) throws Exception {
        role="Expert";
        loadInfoDialog(getStageFromEvent(event));
    }

    @FXML
    private void noviceButtonClicked(ActionEvent event) throws Exception {
        role="Novice";
        loadInfoDialog(getStageFromEvent(event));
    }

    private Stage getStageFromEvent(ActionEvent event) {
        return (Stage) ((Node)event.getSource()).getScene().getWindow();
    }

    @FXML
    private void browseButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(getStageFromEvent(event));
        if (selectedFile != null) {
            image = new Image(selectedFile.toURI().toString());
            imageBig.setImage(image);
        }

    }

    private void loadInfoDialog(Stage s) throws Exception {
        BorderPane infoDialog = FXMLLoader.load(getClass().getResource("./InfoDialog.fxml"));
        s.getScene().setRoot(infoDialog);
    }

    private void loadFieldsList(Stage s) throws Exception {
        BorderPane fieldsList = FXMLLoader.load(getClass().getResource("./FieldsList.fxml"));
        s.getScene().setRoot(fieldsList);
    }

    @FXML
    private void goButtonClicked(ActionEvent event) throws Exception {
        name = nameInput.getText();
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

}
