package console;

import java.io.Console;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;

public class LoginMenu {

    public String initialScreenOption;

    public LoginMenu () {

    }

    public void display(MenuContext menuContext) {
        PrintStream out = menuContext.getOut();
        Scanner scanner = menuContext.getScanner();
        String[] rootScreenOptions = {"Login", "Quit"};
        String[] adminScreenOptions = {"User management", "Auction management", "Log out"};
        String[] userManagementScreenOptions = {"Create user", "Block user", "Unblock user", "Show users", "Show organisations", "Show organisations details", "Go back"};
        String[] userScreenOptions = {"Auction management", "Log out"};
        String[] auctionManagementScreenOptions = {"Create an auction", "See your auctions", "Close an auction", "Bid", "Won bids", "Lost bids", "Go back"};

        List<String> rootScreenOptionsList = Arrays.asList(rootScreenOptions);
        List<String> adminScreenOptionsList = Arrays.asList(adminScreenOptions);
        List<String> userManagementScreenOptionsList = Arrays.asList(userManagementScreenOptions);
        List<String> userScreenOptionsList = Arrays.asList(userScreenOptions);
        List<String> auctionManagementScreenOptionsList = Arrays.asList(auctionManagementScreenOptions);


        String banner = "===================================";
        String optionPrompt = "Enter your option: ";


            out.println(banner);
            for (int i = 0; i < rootScreenOptionsList.size(); i++) {
                out.println((i + 1) + ". " + rootScreenOptionsList.get(i));
            }
            out.print(optionPrompt + "\r\n");
            out.println(banner);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    out.println("Enter username: ");
                    String username = scanner.nextLine();
                    out.println("Enter password: ");
                    String password = scanner.nextLine();
                    out.println(banner);
                    out.println("Username: " + username + "\r\n" + "password: " + password);
                    out.println(banner);
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


}
