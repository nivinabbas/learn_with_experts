package Controllers;

import Client.ClientNetwork;
import Client.UserProberties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConversationController extends GeneralController {
    @FXML
    private TextField messageTextField;
    @FXML
    private ImageView contactImage;
    @FXML
    private Label contactName;
    @FXML
    public VBox messagesVBox;

    public static ConversationController singelton;

    @FXML
    protected void initialize() {
        singelton = this;

        for (var message:UserProberties.getMessages()) {
            if (message.getFrom().getId() == UserProberties.currentContact.getId())
                addMessage(message.getText());
        }
    }

    public static void addMessage(String text) {
        Button messageButton = new Button(text);
        messageButton.setPrefHeight(45);
        singelton.messagesVBox.getChildren().add(messageButton);
        singelton.messagesVBox.setPrefHeight(singelton.messagesVBox.getPrefHeight() + 50);
    }

    @FXML
    void onSendButtonClicked(ActionEvent event) {
        sendMessage();
    }

    void sendMessage() {
        String message = messageTextField.getText();

        if (message.length() > 0) {
            System.out.println("sent message " + message);
            ClientNetwork.sendToServer(String.join(";", "message", "" + UserProberties.currentContact.getId(), message));
            addMessage(message);
            messageTextField.clear();
        }
    }

    @FXML
    void onLeaveButtonClicked(ActionEvent event) {
        endConversation();
    }

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        try {
            load("ExpertList", getStageFromEvent(event));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onTextFieldKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            sendMessage();
        }
    }


    void endConversation() {
        ClientNetwork.sendToServer("end");
        if (ClientNetwork.readFromServer().equals("ok")) {
            try {
                load("ExpertsList", (Stage) contactImage.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
