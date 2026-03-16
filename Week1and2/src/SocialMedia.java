import java.util.*;
public static class SocialMedia
{
    HashMap<String, Integer> users = new HashMap<>();
    HashMap<String, Integer> attempts = new HashMap<>();
    boolean checkAvailability(String username)
    {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
        return !users.containsKey(username);
    }
    void register(String username, int id)
    {
        if (checkAvailability(username))
            {
                users.put(username, id);
            }
    }

    List<String> suggest(String username) {
        List<String> list = new ArrayList<>();
            for(int i=1;i<=3;i++)
            {
                list.add(username + i);
            }
            list.add(username.replace("_","."));
            return list;
        }
    }
public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    SocialMedia sm = new SocialMedia();

    System.out.print("Enter username: ");
    String username = sc.nextLine();

    if (sm.checkAvailability(username)) {
        sm.register(username, 1);
        System.out.println("Username registered successfully");
    }
    else {
        System.out.println("Username not available");
        System.out.println("Suggestions:");
        List<String> suggestions = sm.suggest(username);
        for (String s : suggestions) {
            System.out.println(s);
        }
    }

    sc.close();
}
