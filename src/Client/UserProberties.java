package Client;

import Controllers.ConversationController;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class UserProberties {
    public static String role;
    public static String name;
    public static Image image;
    public static String field;
    public static int id;
    public static User currentContact;
    public static ArrayList<User> onlineUsers = new ArrayList<>();
    private static ArrayList<Message> messages = new ArrayList<>();

    public static void addMessage(User from, String txt) {
        System.out.println("adding message");

        Message message = new Message(from, txt);

        messages.add(message);

        if (ConversationController.singelton != null)
        ConversationController.addMessage(txt);
        else
            System.out.println("failed");
    }

    public static ArrayList<Message> getMessages() {
        return messages;
    }
}
