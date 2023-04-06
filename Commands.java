import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {

    REGISTER("/register i (?<id>\\S+) u (?<username>[\\S]+) p (?<password>[\\S]+)/gm"),
    LOGIN("/login i (?<id>\\S+) p (?<password>[\\S]+)/gm"),
    CREATE_CHANNEL("/create new channel i (?<id>\\S+) u (?<name>[\\S]+)/gm"),
    JOIN_CHANNEL("/join channel i (?<id>\\S+)/gm"),
    CREATE_GROUP("/create new group i (?<id>\\S+) u (?<name>[\\S]+)/gm"),
    START_PV("/start a new private chat with i (?<id>\\S+)/gm"),
    ENTER_CHAT("/enter (?<type>\\S+) i (?<id>\\S+)/gm"),
    SEND_MESSAGE("/send a message c (?<message>.+)/gm"),
    ADD_MEMBER("/add member i (?<id>\\S+)/gm");


    private String regex;
    Commands(String regex){
        this.regex = regex;
    }

    public static Matcher getMatcher(String input , Commands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }

}
