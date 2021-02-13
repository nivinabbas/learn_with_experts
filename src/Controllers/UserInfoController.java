package Controllers;

import Client.UserProberties;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class UserInfoController extends GeneralController {
    @FXML
    private Ellipse userImageEllipse;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label errorMessageLabel;

    private final String defaultImagePath = "src/images/user (4).png";
    private File imageFile;


    @FXML
    protected void initialize() {
        userImageEllipse.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        userImageEllipse.setFill(new ImagePattern(new Image(new File(defaultImagePath).toURI().toString())));
    }

    @FXML
    private void onBrowseButtonClicked(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(getStageFromEvent(event));

        if (selectedFile != null) {
            imageFile = selectedFile;
            userImageEllipse.setFill(new ImagePattern(new Image(imageFile.toURI().toString())));
        }
    }

    @FXML
    private void onGoButtonClicked(ActionEvent event) throws Exception {
        String enteredName = nameTextField.getText();
        if (enteredName.length() > 0) {
            UserProberties.name = enteredName;

            imageFile = imageFile != null ? imageFile : new File(defaultImagePath);

            UserProberties.image = new Image(imageFile.toURI().toString());

            byte[] fileContent = Files.readAllBytes(imageFile.toPath());
            UserProberties.encodedImage = Base64.getEncoder().encodeToString(fileContent);

            Stage s = getStageFromEvent(event);
            load("FieldsList", s);
        } else {
            errorMessageLabel.setText("Name field is required!!");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.06), evt -> errorMessageLabel.setVisible(false)),
                    new KeyFrame(Duration.seconds(0.12), evt -> errorMessageLabel.setVisible(true)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    timeline.stop();
                    errorMessageLabel.setVisible(true);
                }
            };
            timer.schedule(task, 360);
        }
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
