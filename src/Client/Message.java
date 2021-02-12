package Client;

public class Message {
    private User from;
    private String text;

    public Message(User from, String text) {
        this.from = from;
        this.text = text;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
