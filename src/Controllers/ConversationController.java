package Controllers;

import Client.ClientNetwork;
import Client.User;
import Client.UserProberties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class ConversationController extends GeneralController {
    @FXML
    private TextField messageTextField;
    @FXML
    public Label contactRoleField;
    @FXML
    private Label contactName;
    @FXML
    public VBox messagesVBox;
    @FXML
    private Ellipse contactImageEllipse;


    public static ConversationController singelton;

    @FXML
    protected void initialize() {
        singelton = this;

        contactName.setText(UserProberties.currentContact.getName());
        contactImageEllipse.setFill(new ImagePattern(UserProberties.currentContact.getImage()));
        contactRoleField.setText(UserProberties.currentContact.getRole() + " in " + UserProberties.currentContact.getField());

        for (var message : UserProberties.getMessages()) {
            if ((message.getFrom().getId() == UserProberties.currentContact.getId()) || (message.getTo().getId() == UserProberties.currentContact.getId()))
                addMessage(message.getText(), message.getFrom());
        }
    }

    public static void addMessage(String text, User from) {
        try {
            AnchorPane parent = FXMLLoader.load(singelton.getClass().getResource("../FXML/MessageLeft.fxml"));
            HBox messageHBox = (HBox) parent.getChildren().get(0);


            messageHBox.setAlignment(!from.getRole().equals(UserProberties.role)? Pos.BASELINE_LEFT:Pos.BASELINE_RIGHT);
            Label messageLabel = (Label) messageHBox.getChildren().get(0);
            messageLabel.setText(text);
            messageLabel.setEllipsisString("");
            messageLabel.setId(!from.getRole().equals(UserProberties.role)? "messageLeft":"messageRight");
            messageLabel.setAlignment(from.getRole().equals(UserProberties.role)? Pos.TOP_LEFT:Pos.TOP_RIGHT);

            singelton.messagesVBox.getChildren().add(messageHBox);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSendButtonClicked(ActionEvent event) {
        sendMessage(UserProberties.getCurrentUser(), UserProberties.currentContact);
    }

    void sendMessage(User from, User to) {
        String message = messageTextField.getText();

        if (message.length() > 0) {
            System.out.println("sent message " + message);
            ClientNetwork.sendToServer(String.join(";", "message", "" + UserProberties.currentContact.getId(), message));
            UserProberties.addMessage(from, to, message);
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
            UserProberties.currentContact = null;
            load("ExpertsList", getStageFromEvent(event));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onTextFieldKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            sendMessage(UserProberties.getCurrentUser(), UserProberties.currentContact);
        }
    }


    void endConversation() {
        ClientNetwork.sendToServer("end");
        if (ClientNetwork.readFromServer().equals("ok")) {
            try {
                load("ExpertsList", (Stage) contactImageEllipse.getScene().getWindow());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
