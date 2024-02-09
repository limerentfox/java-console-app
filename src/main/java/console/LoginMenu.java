package console;

import java.io.Console;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import static java.lang.System.*;

public class LoginMenu {

    public String initialScreenOption;
    String[] rootScreenOptions = {"Login", "Quit"};
    String[] adminScreenOptions = {"User management", "Auction management", "Log out"};
    String[] userManagementScreenOptions = {"Create user", "Block user", "Unblock user", "Show users", "Show organisations", "Show organisations details", "Go back"};
    String[] userScreenOptions = {"Auction management", "Log out"};
    String[] auctionManagementScreenOptions = {"Create an auction", "See your auctions", "Close an auction", "Bid", "Won bids", "Lost bids", "Go back"};

    List<String> rootScreenOptionsList = Arrays.asList("Login", "Quit");
    List<String> adminScreenOptionsList = Arrays.asList(adminScreenOptions);
    List<String> userManagementScreenOptionsList = Arrays.asList(userManagementScreenOptions);
    List<String> userScreenOptionsList = Arrays.asList(userScreenOptions);
    List<String> auctionManagementScreenOptionsList = Arrays.asList(auctionManagementScreenOptions);


    String banner = "===================================";
    String optionPrompt = "Enter your option: ";

    public LoginMenu () {
    }


    public void display(MenuContext menuContext) {
        PrintStream out = menuContext.getOut();
        Scanner scanner = menuContext.getScanner();
        int choice;
        out.println(banner);
        choice = this.getScreenOption(rootScreenOptionsList, scanner, out);
            switch(choice) {
                case 1:
                    int userChoice;
                    int userSelection;
                    int adminChoice;
                    out.println("Enter username: ");
                    String username = scanner.nextLine();
                    out.println("Enter password: ");
                    String password = scanner.nextLine();

                    if (!username.equalsIgnoreCase("admin")) {
                        out.println(banner);
                        out.println("Welcome " + username);
                        userChoice = this.getScreenOption(userScreenOptionsList, scanner, out);
                        userSelection = this.getUserScreenOption(userChoice, scanner, out);
                    } else {
                        out.println(banner);
                        out.println("Welcome " + username);
                        userChoice = this.getScreenOption(adminScreenOptionsList, scanner, out);
                        adminChoice = this.getAdminScreenOption(userChoice, scanner, out);
                    }

                    break;
                case 2:
                    out.println("Quitting...");
                    System.exit(0);
                    break;
                default:
                    out.println("Invalid choice. Please try again.");
                    return;
            }



    }

    public int getScreenOption(List<String> screen, Scanner scanner, PrintStream out) {
        for (int i = 0; i < screen.size(); i++) {
            out.println((i + 1) + ". " + screen.get(i));
        }
        out.print(optionPrompt + "\r\n");
        out.println(banner);
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public int getAdminScreenOption(int userChoice, Scanner scanner, PrintStream out) {
        int adminChoice = 1;
        switch(userChoice) {
            case 1:
                out.println(banner);
                adminChoice = this.getScreenOption(userManagementScreenOptionsList, scanner, out);
                break;
            case 2:
                out.println(banner);
                adminChoice = this.getScreenOption(auctionManagementScreenOptionsList, scanner, out);
                break;
            case 3:
                int choice;
                out.println("Logging out...");
                out.println(banner);
                choice = this.getScreenOption(rootScreenOptionsList, scanner, out);
                break;
            default:
                out.println("Invalid choice. Please try again.");
                return 1;
        }

        return adminChoice;
    }


    public int getUserScreenOption(int userChoice, Scanner scanner, PrintStream out) {
        int choice = 1;
        switch(userChoice) {
            case 1:
                out.println(banner);
                choice = this.getScreenOption(auctionManagementScreenOptionsList, scanner, out);
                break;
            case 2:
                out.println("Logging out...");
                out.println(banner);
                this.getScreenOption(rootScreenOptionsList, scanner, out);
                break;
            default:
                out.println("Invalid choice. Please try again.");
                return 1;
        }

        return choice;
    }



}
