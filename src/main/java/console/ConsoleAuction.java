package console;


import models.AuctionManager;
import models.User;
import models.UserIdGenerator;
import models.UserStore;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleAuction {
    private final LoginMenu loginMenu;
    public ConsoleAuction() {
        Scanner scanner = new Scanner(System.in);
        PrintStream out =  System.out;
        UserStore userStore = new UserStore();
        int adminId = UserIdGenerator.generateUserId();
        int regularId = UserIdGenerator.generateUserId();

        User adminUser = new User(adminId, "admin", "Christi", "Reid", "password", "admin");
        User regularUser = new User(regularId, "testUser1", "Oliver", "Belanger", "password", "Organisation 2");
        userStore.addUser(adminUser);
        userStore.addUser(regularUser);
        userStore.setCurrentUser(adminUser.getUsername());
        User currentUser = userStore.getCurrentUser();
        AuctionManager auctionManager = new AuctionManager(currentUser);
        loginMenu = new LoginMenu(userStore, scanner, out, auctionManager);
    }

    public void start () {
        loginMenu.display();
    }

    public static void main(String[] args) {
    }

}
