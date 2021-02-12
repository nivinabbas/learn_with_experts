package Controllers;

import Client.ClientNetwork;
import Client.User;
import Client.UserProberties;
import Client.UserService;
import Helpers.CustomException;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        imageMedium.setImage(UserProberties.image);
        nameLabel.setText(UserProberties.name);
        roleLabel.setText(UserProberties.role);

        Service<String> ser = new UserService();

        ConversationController.previousService = ser;

        ser.setOnSucceeded((WorkerStateEvent event) -> {
            String s = ser.getValue();
            System.out.println("received message!! " + s);
            String[] splittedString = s.split(";FayezIbrahimNivin;");

            if (s.startsWith("message")) {
                System.out.println("received message");
                String[] splittedS = s.split(";");
                int fromId = Integer.parseInt(splittedS[1]);
                User from = null;

                for (User u:UserProberties.onlineUsers) {
                    if (u.getId() == fromId)
                        from = u;
                }

                if (from != null)
                    UserProberties.addMessage(from, splittedS[2]);
            } else if (splittedString[0].equals("online")) {
                String userInfo = ClientNetwork.readFromServer();

                String[] splittedUserInfo = userInfo.split(";FayezIbrahimNivin;");
                User onlineUser = new User(Integer.parseInt(splittedUserInfo[0]), splittedUserInfo[1], splittedUserInfo[2], splittedUserInfo[3], "none");
                UserProberties.onlineUsers.add(onlineUser);
                String oppositeRole = UserProberties.role == "Novice" ? "Expert" : "Novice";

                if (onlineUser.getField().equals(UserProberties.field) && onlineUser.getRole().equals(oppositeRole)) {
                    var vBoxChilds = expertsVBox.getChildren();

                    Button onlineUserButton = new Button(onlineUser.getName());

                    onlineUserButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                UserProberties.currentContact = onlineUser;
                                loadConversation(getStageFromEvent(event));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    vBoxChilds.add(onlineUserButton);
                }
            }

            System.out.println("reading again.....");
            ser.restart();
        });
        ser.start();

    }


    private void loadConversation(Stage s) {
        try {
            BorderPane expertsList = FXMLLoader.load(getClass().getResource("../FXML/Conversation.fxml"));
            s.getScene().setRoot(expertsList);
        } catch (Exception e) {
            System.out.println("Error loading conversation");
            e.printStackTrace();
        }
    }

    private void startConversationWith(User u) {
        UserProberties.currentContact = u;

        System.out.println("you are: " + UserProberties.name + ", id:" + UserProberties.id);
        System.out.println("you are connecting with " + u.toString());
        loadConversation((Stage) nameLabel.getScene().getWindow());
    }
}
