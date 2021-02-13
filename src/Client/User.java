package Client;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;


public class User {
    private final String role;
    private final String name;
    private final Image image;
    private final String field;
    private final int id;
    private ArrayList<Message> unSeenMessages = new ArrayList<>();

    public User(int id, String name, String role, String field, String encodedImage) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.field = field;
        System.out.println("enc: " + encodedImage);
        byte[] decodedImage = Base64.getDecoder().decode(encodedImage);
        this.image = new Image(new ByteArrayInputStream(decodedImage));
    }

    public boolean hasUnseenMessages() {
        return unSeenMessages.size() > 0;
    }

    public void addUnseenMessage(Message message) {
        unSeenMessages.add(message);
    }

    public void removeAllUnseenMessages() {
        unSeenMessages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public String getField() {
        return field;
    }

    @Override
    public int hashCode() {
        return this.id;
    }



    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User))
            return false;
        return ((User) o).getId() == this.id;
    }

    @Override
    public String toString() {
        return "User{" +
                "role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", field='" + field + '\'' +
                ", id=" + id +
                '}';
    }
}
