package Controllers;

import Client.ClientNetwork;
import Client.User;
import Client.UserProberties;
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

        Service<String> ser = new Service<String>() {
            public boolean canRestard = true;
            @Override
            protected Task createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws InterruptedException {
                        System.out.println("waiting a message from server......");
                        String s = ClientNetwork.readFromServer();
                        return s;
                    }
                };
            }
        };

        ConversationController.previousService = ser;

        ser.setOnSucceeded((WorkerStateEvent event) -> {
            String s = ser.getValue();

            System.out.println("received message!! " + s);

            String[] splittedString = s.split(";FayezIbrahimNivin;");

            if (splittedString[0].equals("connect")) {
                System.out.println("Connect Request");
                User contact = new User(Integer.parseInt(splittedString[1]), splittedString[2], splittedString[3], splittedString[4], splittedString[5]);
                startConversationWith(contact);
            } else if (splittedString[0].equals("online")) {
                String userInfo = ClientNetwork.readFromServer();

                String[] splittedUserInfo = userInfo.split(";FayezIbrahimNivin;");

                User onlineUser = new User(Integer.parseInt(splittedUserInfo[0]), splittedUserInfo[1], splittedUserInfo[2], splittedUserInfo[3], "none");
                UserProberties.onlineUsers.add(onlineUser);

                var vBoxChilds = expertsVBox.getChildren();

                Button onlineUserButton = new Button(onlineUser.getName());
                onlineUserButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("connecting to user " + onlineUser.getName());

                        System.out.println("Sending to server a connection request");
                        ClientNetwork.sendToServer("connect;" + onlineUser.getId());
                        System.out.println("request send, waiting for response");

                        String response = "ok";
                        if (response.equals("ok")) {
                            try {
                                System.out.println("server responded with" + response);
                                UserProberties.currentContact = onlineUser;
                                loadConversation(getStageFromEvent(event));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            UserProberties.flag1 = false;
                        } else {
                            System.out.println("server responded with "+response);
                        }
                    }
                });
                vBoxChilds.add(onlineUserButton);
            }

            System.out.println("reading again.....");

            if (UserProberties.flag1) {
                ser.restart();
                UserProberties.flag1 = true;
            }
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
        loadConversation((Stage) nameLabel.getScene().getWindow());
    }
}
