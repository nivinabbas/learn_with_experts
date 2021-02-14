package Client;

public class Message {
    private final User from;
    private final User to;
    private final String text;

    public Message(User from, User to, String text) {
        this.from = from;
        this.text = text;
        this.to = to;
    }

    public User getTo() {
        return to;
    }

    public User getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }
}
