package Client;

import javafx.scene.image.Image;


public class User {
    private String role;
    private String name;
    private Image image;
    private String field;

    public User(String name, Image image, String field, String role) {
        this.role = role;
        this.name = name;
        this.image = image;
        this.field = field;
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

    public String getField() {
        return field;
    }
}
