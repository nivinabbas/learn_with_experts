package Controllers;

import Client.ClientNetwork;
import Client.UserProberties;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
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
    public VBox messagesVBox;

    @FXML
    private Button leaveButton;
    @FXML
    private Button backButton;

    public static Service<String> previousService;

    public static ConversationController singelton;

    @FXML
    protected void initialize() {
        imageMedium.setImage(UserProberties.image);
        nameLabel.setText(UserProberties.name);
        roleLabel.setText(UserProberties.role);

        contactNameLabel.setText(UserProberties.currentContact.getName());
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
        singelton.messagesVBox.setPrefHeight(singelton.messagesVBox.getPrefHeight()+50);
    }

    @FXML
    void onSendButtonClicked(ActionEvent event) {
        String message = messageField.getText();
        System.out.println("sent message " + message);
        ClientNetwork.sendToServer( String.join(";", "message", ""+UserProberties.currentContact.getId(), message));
        addMessage(message);
    }

    @FXML
    void onLeaveButtonClicked(ActionEvent event) {
        endConversation();
    }

    @FXML
    void onBackButtonPressed(ActionEvent event) {
        load((Stage) backButton.getScene().getWindow(), "ExpertList");
    }


    void endConversation() {
        ClientNetwork.sendToServer("end");
        if (ClientNetwork.readFromServer().equals("ok")) {
            load((Stage) leaveButton.getScene().getWindow(), "ExpertsList");
        }
    }

    private void load(Stage s, String name) {
        try {
            BorderPane expertsList = FXMLLoader.load(getClass().getResource("../FXML/" + name + ".fxml"));
            s.getScene().setRoot(expertsList);
        } catch (Exception e) {
            System.out.println("Error loading ExpertList");
            e.printStackTrace();
        }
    }
}
