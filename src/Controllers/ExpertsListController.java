package Controllers;

import Client.ClientNetwork;
import Client.User;
import Client.UserProberties;
import Client.UserService;
import Helpers.CustomException;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExpertsListController extends GeneralController {
    @FXML
    private ImageView userImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label userRoleLabel;
    @FXML
    private VBox onlineUsersVBox;
    private double cloneButtonPrefWidth;
    private double cloneButtonPrefHeight;
    private Node cloneButtonGraphic;

    @FXML
    protected void initialize() {
        nameLabel.setText(UserProberties.name);
        userRoleLabel.setText(UserProberties.role);
        userImage.setImage(UserProberties.image);

        onlineUsersVBox.getChildren().clear();

        try {
            ClientNetwork.connectToServer();
        } catch (Exception e) {
            try {
                throw new CustomException("Error connecting to server");
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }

        Service<String> ser = new UserService();

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
                User onlineUser = new User(Integer.parseInt(splittedUserInfo[0]), splittedUserInfo[1], splittedUserInfo[2], splittedUserInfo[3], splittedUserInfo[4]);
                UserProberties.onlineUsers.add(onlineUser);
                String oppositeRole = UserProberties.role == "Novice" ? "Expert" : "Novice";

                if (onlineUser.getField().equals(UserProberties.field) && onlineUser.getRole().equals(oppositeRole)) {
                    var flowPaneChilds = onlineUsersVBox.getChildren();

                    Button onlineUserButton = new Button(onlineUser.getName());
                    ImageView img = new ImageView(onlineUser.getImage());

                    img.setFitHeight(28);
                    img.setFitWidth(35);
                    img.setStyle("-fx-padding: 8px 16px");
                    onlineUserButton.setStyle("-fx-padding:8px 16px;");
                    onlineUserButton.setMinWidth(168);
                    onlineUserButton.setText(onlineUser.getName());
                    onlineUserButton.setGraphic(img);
                    onlineUserButton.setPrefHeight(cloneButtonPrefHeight);
                    onlineUserButton.setPrefWidth(cloneButtonPrefWidth);

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
                    flowPaneChilds.add(onlineUserButton);
                }
            }

            System.out.println("reading again.....");
            ser.restart();
        });
        ser.start();

    }


    private void loadConversation(Stage s) {
        try {
            AnchorPane expertsList = FXMLLoader.load(getClass().getResource("../FXML/Conversation.fxml"));
            s.getScene().setRoot(expertsList);
        } catch (Exception e) {
            System.out.println("Error loading conversation");
            e.printStackTrace();
        }
    }

    @FXML
    private void onBackButtonClicked(ActionEvent event) {

    }

    private void startConversationWith(User u) {
        try {
            UserProberties.currentContact = u;

            System.out.println("you are: " + UserProberties.name + ", id:" + UserProberties.id);
            System.out.println("you are connecting with " + u.toString());
            load("Conversation", (Stage) nameLabel.getScene().getWindow());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
