package Controllers;

import Client.User;
import Client.UserProberties;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ExpertsListController extends GeneralController {
    @FXML
    private ImageView imageMedium;
    @FXML
    private Label nameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label expertListTitle;
    @FXML
    private VBox expertsVBox;

    @FXML
    protected void initialize() {
            imageMedium.setImage(UserProberties.image);
            nameLabel.setText(UserProberties.name);
            roleLabel.setText(UserProberties.role);
            String t = expertListTitle.getText();
            t += UserProberties.field;
            expertListTitle.setText(t);

            for (User user : UserProberties.onlineUsers) {
                if (user.getRole().equals("Expert")) {
                    Button expertButton = new Button(user.getName());
                    ImageView imageView = new ImageView(user.getImage());
                    expertButton.setGraphic(imageView);
                    expertsVBox.getChildren().add(expertButton);
                }
            }
    }
}
