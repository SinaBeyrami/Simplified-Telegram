import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {

    public void run(Scanner scanner){
        while(true) {
            String command = scanner.nextLine().trim();
            Matcher matcher;
            if (command.equals("exit")){
                return;
            }else if((matcher = Commands.getMatcher(command , Commands.REGISTER)) != null){
                System.out.println(register(matcher));
            }else if((matcher = Commands.getMatcher(command , Commands.LOGIN)) != null){
                System.out.println(login(matcher));
                if((login(matcher).equals("User successfully logged in!")) && (Messenger.getUserById(matcher.group("id")) != null)){
                    User thisUser = Messenger.getUserById(matcher.group("id"));
                    MessengerMenu messengerMenu = new MessengerMenu(thisUser);
                    messengerMenu.run(scanner);
                }
            }else{
                System.out.println("Invalid command!");
            }
        }
    }

    private String login(Matcher matcher){
        String id = matcher.group("id");
        String password = matcher.group("password");
        if (Messenger.getUserById(id) == null)
            return ("No user with this id exists!");
        else if (!(Messenger.getUserById(id).getPassword().equals(password)))
            return ("Incorrect password!");
        return ("User successfully logged in!");
    }

    private String register(Matcher matcher) {
        String id = matcher.group("id");
        String username = matcher.group("username");
        String password = matcher.group("password");
        if(!(username.matches("^[a-zA-Z0-9_]+$")))
            return ("Username's format is invalid!");
        else if (!(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,?/~_-+=|]).{8,32}$")))
            return ("Password is weak!");
        else if (Messenger.getUserById(id) != null)
            return ("A user with this ID already exists!");
        User newUser = new User(id , username , password);
        Messenger.addUser(newUser);
        return("User has been created successfully!");
    }

}
