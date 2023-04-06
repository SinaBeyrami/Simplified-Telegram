import java.util.Scanner;
import java.util.regex.Matcher;

public class MessengerMenu {

    private Chat chat;
    private User currentUser;

    public MessengerMenu(User currentUser){
        this.currentUser = currentUser;
    }

    public void run(Scanner scanner){
        while(true) {
            String command = scanner.nextLine().trim();
            Matcher matcher;
            if (command.equals("show all channels"))
                System.out.println(showAllChannels());
            else if ((matcher = Commands.getMatcher(command , Commands.CREATE_CHANNEL)) != null)
                System.out.println(createChannel(matcher));
            else if ((matcher = Commands.getMatcher(command , Commands.JOIN_CHANNEL)) != null)
                System.out.println(joinChannel(matcher));
            else if (command.equals("show my chats"))
                System.out.println(showChats());
            else if ((matcher = Commands.getMatcher(command , Commands.CREATE_GROUP)) != null)
                System.out.println((createGroup(matcher)));
            else if ((matcher = Commands.getMatcher(command , Commands.START_PV)) != null)
                System.out.println(createPrivateChat(matcher));
            else if ((matcher = Commands.getMatcher(command , Commands.ENTER_CHAT)) != null){
                System.out.println(enterChat(matcher));
                if (enterChat(matcher) == "You have successfully entered the chat!"){
                    ChatMenu chatMenu = new ChatMenu(currentUser);
                    chatMenu.run(scanner , currentUser.getChatById(matcher.group("id")));
                }
            }else if (command.equals("logout")){
                System.out.println("Logged out");
                return;
            }else {
                System.out.println("Invalid command!");
                return;
            }
        }
    }

    private String showAllChannels(){
        String str = "All Channels:\n";
        int i = 1;
        for (Channel channel : Messenger.getChannels()){
            str = str + i + ". " + channel.getName() + ", id: " + channel.getId() + ", members: " + channel.getMembers().size() + "\n" ;
            i++;
        }
        return str;
    }

    private String showChats(){
        String str = "Chats:\n";
        int i = 1;
        for (Chat chat : currentUser.getChats()){
            str = str + i +". " + chat.getName() + ", id: " + chat.getId() + ", " + chat.getClass().getSimpleName() + "\n";
            i++;
        }
        return str;
    }

    private String enterChat(Matcher matcher){
        String id = matcher.group("id");
        String chatType = matcher.group("type");
        for (Chat chat : currentUser.getChats()){
            if (chat.getId().equals(id)){
                return ("You have successfully entered the chat!");
            }
        }
        return ("You have no" + chatType + "with this id!");
    }

    private String createChannel(Matcher matcher){
        String id = matcher.group("id");
        String name = matcher.group("name");
        if (!(name.matches("^[a-zA-Z0-9_]+$")))
            return ("Channel name's format is invalid!");
        else if (Messenger.getChannelById(id) != null)
            return ("A channel with this id already exists!");
        Channel newChannel = new Channel(currentUser , id , name);
        Messenger.addChannel(newChannel);
        newChannel.addMember(currentUser);
        currentUser.addChannel(newChannel);
        return ("Channel" + name + "has been created successfully!");
    }

    private String createGroup(Matcher matcher){
        String id = matcher.group("id");
        String name = matcher.group("name");
        if (!(name.matches("^[a-zA-Z0-9_]+$")))
            return ("Group name's format is invalid!");
        else if (Messenger.getGroupById(id) != null)
            return ("A group with this id already exists!");
        Group newGroup = new Group(currentUser , id , name);
        Messenger.addGroup(newGroup);
        newGroup.addMember(currentUser);
        currentUser.addGroup(newGroup);
        return ("Group" + name + "has been created successfully!");
    }

    private String createPrivateChat(Matcher matcher){
        String id = matcher.group("id");
        if (Messenger.getUserById(id) == null)
            return ("No user with this id exists!");
        else if (currentUser.getPrivateChatById(id) != null)
            return ("You already have a private chat with this user!");
        PrivateChat newPvForCurrentUser = new PrivateChat(currentUser , id , Messenger.getUserById(id).getName());
        PrivateChat newPvForTargetUser = new PrivateChat(currentUser , currentUser.getId() , currentUser.getName());
        currentUser.addPrivateChat(newPvForCurrentUser);
        Messenger.getUserById(id).addPrivateChat(newPvForTargetUser);
        return ("Private chat with" +  Messenger.getUserById(id).getName() + "has been started successfully!");
    }

    private String joinChannel(Matcher matcher){
        String channelId = matcher.group("id");
        if (Messenger.getChannelById(channelId) == null)
            return("No channel with this id exists!");
        else if (currentUser.getChannelById(channelId) != null)
            return("You're already a member of this channel!");
        currentUser.addChannel(Messenger.getChannelById(channelId));
        Messenger.getChannelById(channelId).addMember(currentUser);
        return("You have successfully joined the channel!");
    }

}
