public class Message {

    private User owner;
    private String Content;

    public Message(User owner , String content){
        this.owner = owner;
        this.Content = content;
    }

    public User getOwner() {
        return owner;
    }

    public String getContent() {
        return Content;
    }
}
