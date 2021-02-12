package Controllers;

import Client.ClientNetwork;
import Client.User;
import Client.UserProberties;
import Helpers.CustomException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Timer;
import java.util.TimerTask;

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
        try {
            ClientNetwork.connectToServer();
        } catch (Exception e) {
            try {
                throw new CustomException("Error connecting to server");
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    UserProberties.onlineUsers.clear();

                    ClientNetwork.readOnlineUsers();
                    System.out.println("There is " + UserProberties.onlineUsers.size() + " online users");


                    imageMedium.setImage(UserProberties.image);
                    nameLabel.setText(UserProberties.name);
                    roleLabel.setText(UserProberties.role);
                    String t = expertListTitle.getText();
                    t += UserProberties.field;
                    expertListTitle.setText(t);

                    var vBoxChilds = expertsVBox.getChildren();

                    vBoxChilds.clear();

                    for (User u : UserProberties.onlineUsers) {
                        System.out.print(u.getName() + ", ");
                    }

                    for (User user : UserProberties.onlineUsers) {
                        System.out.println(user.getName() + " is online");
                        System.out.println("size is " + UserProberties.onlineUsers.size());
                        Button expertButton = new Button(user.getName());
//                    ImageView imageView = new ImageView(user.getImage());
//                    expertButton.setGraphic(imageView);
                        vBoxChilds.add(expertButton);
                    }
                });

            }
        }, 0, 7000);

    }
}
