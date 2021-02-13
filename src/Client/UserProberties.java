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
    private static final ArrayList<Message> messages = new ArrayList<>();
    public static String encodedImage;
    public static UserService userService;

    public static void addMessage(User from, User to, String txt) {
        System.out.println("adding message");

        Message message = new Message(from, to, txt);

        messages.add(message);
        if (currentContact == null) {
            System.out.println("Current contact is null, returning");
            return;
        }
        System.out.println("checking if matching " + from.getId() + " and " + currentContact.getId());
        if (ConversationController.singelton != null && (from.getId() == currentContact.getId() || to.getId() == currentContact.getId())) {
            if(txt.length()>34)
                txt+="\n";
            ConversationController.addMessage(txt);


        } else
            System.out.println("failed to add message");
    }

    public static User getCurrentUser() {
        return new User(id, name, role, field, encodedImage);
    }

    public static ArrayList<Message> getMessages() {
        return messages;
    }
}
