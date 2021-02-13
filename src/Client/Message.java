package Client;

public class Message {
    private User from;
    private User to;
    private String text;

    public Message(User from, User to, String text) {
        this.from = from;
        this.text = text;
        this.to = to;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
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
