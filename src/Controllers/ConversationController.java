package Controllers;

import Client.ClientNetwork;
import Client.UserProberties;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConversationController extends GeneralController {
    @FXML
    private ImageView imageMedium;
    @FXML
    private Label nameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private TextField messageField;
    @FXML
    private ImageView contactImage;
    @FXML
    private Label contactNameLabel;
    @FXML
    private VBox messagesVBox;
    @FXML
    private Button leaveButton;
    public static Service<String> previousService;

    @FXML
    protected void initialize() {
        imageMedium.setImage(UserProberties.image);
        nameLabel.setText(UserProberties.name);
        roleLabel.setText(UserProberties.role);
        contactNameLabel.setText(UserProberties.currentContact.getName());

        if (previousService != null) {
            previousService.cancel();
            previousService.setOnSucceeded(null);
        }

        Service<String> ser = new Service<String>() {
            @Override protected Task createTask() {
                return new Task<String>() {
                    @Override protected String call() throws InterruptedException {
                        System.out.println("reading from server ");
                        String s = ClientNetwork.readFromServer();
                        return s;
                    }
                };
            }
        };

        ser.setOnSucceeded((WorkerStateEvent event) -> {
            String s = ser.getValue();
            System.out.println("received message!! "+s);
            if (s.equals("end")) {
                endConversation();
            } else {
                addMessage(s);
                ser.restart();
            }
        });

        ser.start();
    }

    void addMessage(String text) {
        Button messageButton = new Button(text);
        messageButton.setPrefHeight(45);
        messagesVBox.getChildren().add(messageButton);
        messagesVBox.setPrefHeight(messagesVBox.getPrefHeight()+50);
    }

    @FXML
    void onSendButtonClicked(ActionEvent event) {
        String message = messageField.getText();
        System.out.println("sent message "+message);
        ClientNetwork.sendToServer("message;"+message);
        addMessage(message);
    }

    @FXML
    void onLeaveButtonClicked(ActionEvent event) {
        endConversation();
    }

    void endConversation() {
        ClientNetwork.sendToServer("end");
        if (ClientNetwork.readFromServer().equals("ok")) {
            try {
                load((Stage) leaveButton.getScene().getWindow(),"ExpertsList");
            } catch (Exception e) {
                System.out.println("Error loading ExpertList");
                e.printStackTrace();
            }
        }
    }

    private void load(Stage s, String name) throws Exception {
        BorderPane expertsList = FXMLLoader.load(getClass().getResource("../FXML/"+name+".fxml"));
        s.getScene().setRoot(expertsList);
    }
}
