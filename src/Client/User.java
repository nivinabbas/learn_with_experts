package Client;

import javafx.scene.image.Image;


public class User {
    private final String role;
    private final String name;
    private Image image;
    private final String field;
    private final int id;

    public User(int id, String name, String role, String field, String b64Image) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.field = field;
//        byte[] decodedImage = Base64.getDecoder().decode(b64Image);
//        this.image = new Image(new ByteArrayInputStream(decodedImage));
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
