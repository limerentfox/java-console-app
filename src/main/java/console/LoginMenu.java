package console;

import models.*;
import java.io.PrintStream;
import java.util.Scanner;

public class LoginMenu {
    private final Scanner scanner;
    private final PrintStream out;
    private final UserStore userStore;
    private final AuctionMenuManager auctionMenuManager;
    private final UserManagementMenu userManagementMenu;

    public LoginMenu(UserStore userStore, Scanner scanner, PrintStream out, AuctionManager auctionManager) {
        this.scanner = scanner;
        this.out = out;
        this.userStore = userStore;
        this.auctionMenuManager = new AuctionMenuManager(scanner, out, auctionManager);
        this.userManagementMenu = new UserManagementMenu(scanner, out, userStore);
    }

    public void display() {
        while (true) {
            String rootMenu = "\n===================================\n1. Login\n2. Quit\n===================================\nEnter option: ";
            String option = getMenuOption(rootMenu);

            switch (option) {
                case "1":
                    loginUser();
                    break;
                case "2":
                    out.println("Quitting...");
                    return; // Exit the application
                default:
                    out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void loginUser() {
        out.println(userStore.getAllUsers().values());
        out.print("Enter username: ");
        String username = scanner.nextLine();
        out.print("Enter password: ");
        String password = scanner.nextLine();

        User currentUser;

        try {
            currentUser = userStore.authenticateUser(username, password);
            auctionMenuManager.setCurrentUser(currentUser);
            String banner = "===================================";
            out.println(banner);
            out.println("Welcome " + currentUser.getFullName());

            if (currentUser.getIsAdmin()) {
                this.showAdminMenu();
            } else {
                this.showUserMenu();
            }
        } catch (BusinessException e) {
            out.println("Error logging in: " + e.getMessage());
            return;
        }
    }

    private String getMenuOption(String menuOptions) {
        out.println(menuOptions);
        return scanner.nextLine();
    }

    private void showUserMenu() {
        boolean running = true;
        while (running) {
            String userMenu = "\n===================================\n1. Auction management\n2. logout\n===================================\nEnter option: ";
            String option = getMenuOption(userMenu);
            switch (option) {
                case "1":
                    // Auction management
                    auctionMenuManager.displayAuctionMenu();
                    break;
                case "2":
                    // Logout
                    out.println("Logging out...");
                    this.resetCurrentUser();
                    running = false;
                    break;
                default:
                    out.println("Invalid option.");
                    break;
            }
        }
    }

    private void showAdminMenu() {
        boolean running = true;
        while (running) {
            String adminMenuOptions = "1. User management\n2. Auction management\n3. Logout\n===================================\nEnter option: ";
            String option = getMenuOption(adminMenuOptions);
            switch (option) {
                case "1":
                    // User management
                    userManagementMenu.displayUserManagementMenu();
                    break;
                case "2":
                    // Auction management
                    auctionMenuManager.displayAuctionMenu();
                    break;
                case "3":
                    // Logout
                    out.println("Logging out...");
                    this.resetCurrentUser();
                    running = false;
                    break;
                default:
                    out.println("Invalid option.");
                    break;
            }
        }
    }

    private void resetCurrentUser() {
        auctionMenuManager.setCurrentUser(null);
    }
}

