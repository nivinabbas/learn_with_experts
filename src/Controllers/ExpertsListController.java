package Controllers;

import Client.ClientNetwork;
import Client.User;
import Client.UserProberties;
import Client.UserService;
import Helpers.CustomException;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ExpertsListController extends GeneralController {
    @FXML
    private Ellipse userImageEllipse;
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
        userImageEllipse.setFill(new ImagePattern(UserProberties.image));
        userImageEllipse.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

        onlineUsersVBox.getChildren().clear();

        try {
            if (!ClientNetwork.isConnected())
                ClientNetwork.connectToServer();
        } catch (Exception e) {
            try {
                throw new CustomException("Error connecting to server");
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }

        for (User user : UserProberties.onlineUsers) {
            addOnlineUserIfPossible(user);
        }

        if (UserProberties.userService == null) {
            UserProberties.userService = new UserService();

            UserProberties.userService.setOnSucceeded((WorkerStateEvent event) -> {
                String reddenValue = UserProberties.userService.getValue();

                if (reddenValue == null) {
                    UserProberties.userService = null;
                    UserProberties.onlineUsers = new ArrayList<>();
                    return;
                }

                System.out.println("received message!! " + reddenValue);
                String[] splittedString = reddenValue.split(";FayezIbrahimNivin;");

                if (reddenValue.startsWith("message")) {
                    System.out.println("received message");
                    String[] splittedS = reddenValue.split(";");
                    int fromId = Integer.parseInt(splittedS[1]);
                    User from = null;

                    for (User u : UserProberties.onlineUsers) {
                        if (u.getId() == fromId)
                            from = u;
                    }

                    if (from != null)
                        UserProberties.addMessage(from, UserProberties.getCurrentUser(), splittedS[2]);
                } else if (splittedString[0].equals("online")) {
                    String userInfo = ClientNetwork.readFromServer();

                    String[] splittedUserInfo = userInfo.split(";FayezIbrahimNivin;");
                    User onlineUser = new User(Integer.parseInt(splittedUserInfo[0]), splittedUserInfo[1], splittedUserInfo[2], splittedUserInfo[3], splittedUserInfo[4]);
                    UserProberties.onlineUsers.add(onlineUser);
                    addOnlineUserIfPossible(onlineUser);
                } else if (splittedString[0].startsWith("offline")) {
                    int offlineUserId = Integer.parseInt(splittedString[1]);
                    User offlineUser = null;

                    for (User u : UserProberties.onlineUsers) {
                        if (u.getId() == offlineUserId)
                            offlineUser = u;
                    }

                    if (offlineUser != null) {
                        System.out.println("offline user " + UserProberties.onlineUsers.remove(offlineUser));
                    }

                    if (onlineUsersVBox != null) {
                        System.out.println("offline user removed from vbox");
                        onlineUsersVBox.getChildren().clear();
                        for (User u : UserProberties.onlineUsers) {
                            addOnlineUserIfPossible(u);
                        }
                    } else {
                        System.out.println("vbox is null");
                    }
                }
                UserProberties.userService.restart();
            });

            UserProberties.userService.start();
        }

    }

    private void addOnlineUserIfPossible(User user) {
        String oppositeRole = UserProberties.role == "Novice" ? "Expert" : "Novice";
        if (onlineUsersVBox != null && user.getField().equals(UserProberties.field) && user.getRole().equals(oppositeRole)) {
            var flowPaneChilds = onlineUsersVBox.getChildren();

            Button onlineUserButton = new Button(user.getName());
            Ellipse el = new Ellipse(0, 0, 23, 20);
            el.setFill(new ImagePattern(user.getImage()));

            onlineUserButton.setStyle("-fx-margin: 166px");
            onlineUserButton.setStyle("-fx-padding:30px 16px;");
            onlineUserButton.setMinWidth(168);
            onlineUserButton.setText(user.getName());
            onlineUserButton.setGraphic(el);
            onlineUserButton.setPrefHeight(cloneButtonPrefHeight);
            onlineUserButton.setPrefWidth(cloneButtonPrefWidth);

            onlineUserButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        UserProberties.currentContact = user;
                        loadConversation(getStageFromEvent(event));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            flowPaneChilds.add(onlineUserButton);
        }
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
    private void onBackButtonClicked(ActionEvent event) throws Exception {
        ClientNetwork.disconnect();
        load("FieldsList", getStageFromEvent(event));
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
