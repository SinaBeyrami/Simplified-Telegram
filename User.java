import java.util.ArrayList;

public class User {

    private ArrayList<Chat> chats = new ArrayList<Chat>();
    private String id;
    private String name;
    private String password;

    public User(String id , String name , String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void addChat(Chat chat){

    }

    public void addGroup(Group group){

    }

    public void addChannel(Channel channel){
        this.chats.add(channel);
    }

    public void addPrivateChat(PrivateChat pv){

    }

    public Group getGroupById(String id){
        for (Chat group : chats){
            if (group.getId().equals(id))
                return (Group) group;
        }
        return null;
    }

    public Channel getChannelById(String id){
        for (Chat channel : chats){
            if (channel.getId().equals(id))
                return (Channel) channel;
        }
        return null;
    }

    public PrivateChat getPrivateChatById(String id){
        for (Chat pv : chats){
            if(pv.getId().equals(id))
                return (PrivateChat) pv;
        }
        return null;
    }

    public Chat getChatById(String id){
        for (Chat chat : chats){
            if(chat.getId().equals(id))
                return chat;
        }
        return null;
    }

}
