package console;


import models.User;
import models.UserIdGenerator;
import models.UserStore;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleAuction {
    private final MenuContext menuContext;

    public ConsoleAuction() {
        Scanner scanner = new Scanner(System.in);
        PrintStream out =  System.out;
        UserStore userStore = new UserStore();
        int adminId = UserIdGenerator.generateUserId();
        int regularId = UserIdGenerator.generateUserId();

        User adminUser = new User("admin", "Christi", "Reid", "password", "admin", adminId, true, false);
        User regularUser = new User("regularUser", "Oliver", "Belanger", "password", "Organisation 2", regularId, false, false);
        userStore.addUser(adminUser);
        userStore.addUser(regularUser);


        menuContext = new MenuContext(userStore, scanner, out);
    }

    public void start () {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.display(menuContext);
    }

    public static void main(String[] args) {
    }

}
