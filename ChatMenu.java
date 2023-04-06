import java.util.Scanner;
import java.util.regex.Matcher;

public class ChatMenu {

    private Chat chat;
    private User currentUser;

    public ChatMenu(User currentUser){
        this.currentUser = currentUser;
    }

    public void run(Scanner scanner , Chat chat){
        while(true) {
            String command = scanner.nextLine().trim();
            Matcher matcher;
            if ((matcher = Commands.getMatcher(command, Commands.SEND_MESSAGE)) != null)
                System.out.println(sendMessage(matcher));
            else if ((matcher = Commands.getMatcher(command, Commands.ADD_MEMBER)) != null)
                System.out.println(addMember(matcher));
            else if (command.equals("show all messages"))
                System.out.println(showMessages());
            else if (command.equals("show all members"))
                System.out.println(showMembers());
            else if (command.equals("back"))
                return;
        }
    }

    private String showMessages() {
        String str = "Messages:\n";
        for (Message message : chat.getMessages()) {
            str = str + (message.getOwner().getName()) + "(" + (message.getOwner().getId()) + "): " + "\"" + (message.getContent()) + "\"\n";
        }
        return (str);
    }

    private String showMembers(){
        if (chat instanceof PrivateChat)
            return ("Invalid command!");
        String str = "Members:\n";
        for (User member : chat.getMembers()){
            if (member.getId().equals(chat.getOwner().getId()))
                str = str + "name: " + (member.getName()) + ", id: " + (member.getId()) + "*owner\n";
            else
                str = str + "name: " + (member.getName()) + ", id: " + (member.getId()) + "\n";
        }
        return (str);
    }

    private String addMember(Matcher matcher){
        String id = matcher.group("id");
        if (chat instanceof PrivateChat)
            return ("Invalid command!");
        if (!(currentUser.getId().equals(chat.getOwner().getId())))
            return ("You don't have access to add a member!");
        if (Messenger.getUserById(id) == null)
            return ("No user with this id exists!");
        for (User user : chat.getMembers()){
            if (currentUser.getId().equals(user.getId()))
                return ("This user is already in the chat!");
        }
        if (chat instanceof Group){
            String content = (Messenger.getUserById(id).getName() + "has been added to the group!");
            Message addMemberMessage = new Message(currentUser , content);
            chat.addMessage(addMemberMessage);
        }
        return ("User has been added successfully!");
    }

    private String sendMessage(Matcher matcher){
        String content = matcher.group("message");
        Message newMessage = new Message(currentUser , content);
        if ((chat instanceof Channel) && !(currentUser.getId().equals(chat.getOwner().getId())))
            return ("You don't have access to send a message!");
        chat.addMessage(newMessage);
        return ("Message has been sent successfully!");
    }

}
