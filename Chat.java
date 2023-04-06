import java.util.ArrayList;

public class Chat {

    private ArrayList<User> members = new ArrayList<User>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private User owner;
    private String id;
    private String name;

    public Chat(User owner , String id, String name){
        this.owner = owner;
        this.id = id;
        this.name = name;
    }

    public void addMember(User user){
        this.members.add(user);
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public User getOwner() {
        return owner;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
