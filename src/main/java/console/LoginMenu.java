package console;

import models.User;
import models.UserIdGenerator;
import models.UserStore;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Scanner;

public class LoginMenu {
    String banner = "===================================";
    String optionPrompt = "Enter your option: ";
    String rootMenuOptions = "\n===================================\n1. Login\n2. Quit\n===================================\nEnter option: ";



    public LoginMenu () {
    }


    public void display(MenuContext menuContext) {
        while (true) {
            UserStore userStore = menuContext.getUserStore();
            PrintStream out = menuContext.getOut();
            Scanner scanner = menuContext.getScanner();
            String initialOption = getMenuOption(rootMenuOptions, scanner, out);

            switch (initialOption) {
                case "1":
                    out.print("Enter username: ");
                    String username = scanner.nextLine();
                    out.print("Enter password: ");
                    String password = scanner.nextLine();

                    User user = userStore.getUser(userStore.getUsers(), username, password);
                    menuContext.setCurrentUser(user);

                    if (user == null) {
                        out.println("Username or password does not match our records. Try logging in again.");
                    }
                    if (user != null && !user.getIsAdmin()) {
                        out.println(banner);
                        out.println("Welcome " + user.getFullName());
                        boolean showUserMenu = true;
                        while (showUserMenu) {
                            showUserMenu = showUserMenu(scanner, out, userStore);
                        }
                    }

                    if (user != null && user.getIsAdmin()) {
                        out.println(banner);
                        out.println("Welcome " + user.getFullName());
                        boolean showAdminMenu = true;
                        while (showAdminMenu) {
                           showAdminMenu = showAdminMenu(scanner, out, userStore);
                        }
                    }

                    break;
                case "2":
                    out.println("Quitting...");
                    System.exit(0);
                    break;
                default:
                    out.println("Invalid choice. Please try again.");
            }
        }
    }

        private static String getMenuOption(String menuOptions, Scanner scanner, PrintStream out) {
            out.println(menuOptions);
            return scanner.nextLine();
        }
        private static Boolean showAdminMenu (Scanner scanner, PrintStream out, UserStore userStore) {
            String adminMenuOptions = "1. User management\n2. Auction management\n3. Logout\n===================================\nEnter option: ";
            String option = getMenuOption(adminMenuOptions, scanner, out);
            switch (option) {
                case "1":
                    // User management
                    boolean showUserManagementMenu = true;
                    while (true) {
                        showUserManagementMenu = showUserManagementMenu(scanner, out, userStore);
                    }
                case "2":
                    // Auction management
                    boolean showAuctionManagementMenu = true;
                    while (true) {
                        showAuctionManagementMenu = showAuctionManagementMenu(scanner, out, userStore);
                    }
                case "3":
                    // Logout
                    out.println("Logging out...");
                   break;
                default:
                    out.println("Invalid option.");
                    break;
            }
            return true;
        }

    private static boolean showUserManagementMenu(Scanner scanner, PrintStream out, UserStore userStore) {
            String userManagementMenu = "\n===================================\n1. Create user\n2. Block user\n3. Unblock user\n4. Show users\n5. Show organisations\n6. Show organisation details\n7. Go back\n===================================\nEnter option: ";
            String option = getMenuOption(userManagementMenu, scanner, out);
            switch (option) {
                case "1":
                    // Create user
                    out.println("Enter username: ");
                    String username = scanner.nextLine();
                    out.println("Enter password: ");
                    String password = scanner.nextLine();
                    out.println("Enter first name: ");
                    String firstName = scanner.nextLine();
                    out.println("Enter last name: ");
                    String lastName = scanner.nextLine();
                    out.println("Enter organisation: ");
                    String organisation = scanner.nextLine();
                    int regularId = UserIdGenerator.generateUserId();
                    User regularUser = new User(username, firstName, lastName, password, organisation, regularId, username.equalsIgnoreCase("admin") || organisation.equalsIgnoreCase("admin"), false);
                    userStore.addUser(regularUser);
                    out.println("User has been successfully created");
                    break;
                case "2":
                    // Block user
                    out.println("Enter username of user to block: ");
                    String usernameToBlock = scanner.nextLine();
                    User userToBeBlocked = userStore.findByUsername(userStore.getUsers(), usernameToBlock);
                    userToBeBlocked.setIsBlocked(true);
                    out.println(userToBeBlocked.getFullName() + " is now blocked");
                    break;
                case "3":
                    // Unblock user
                    out.println("Enter username of user to unblock: ");
                    String usernameToUnblock = scanner.nextLine();
                    User userToBeUnblocked = userStore.findByUsername(userStore.getUsers(), usernameToUnblock);
                    userToBeUnblocked.setIsBlocked(false);
                    out.println(userToBeUnblocked.getFullName() + " is now unblocked");
                    break;
                case "4":
                    // Show users
                    listUsers(userStore, scanner, out);
                    break;
                case "5":
                    // Show organisations
                    listOrganisations(userStore, scanner, out);
                    break;
                case "6":
                    // Show organisation details
                    printUsersByOrganization(userStore, out);
                    break;
                case "7":
                    // Go back
                    showAdminMenu(scanner, out, userStore);
                default:
                    out.println("Invalid option.");
                    break;
            }
            return true;
        }




    private static void listUsers(UserStore userStore, Scanner scanner, PrintStream out) {
        List<User> users = new ArrayList<>(userStore.getUsers().values());
        int page = 0;
        final int pageSize = 3; // Number of users to display per page

        while (true) {
            int start = page * pageSize;
            int end = Math.min(start + pageSize, users.size());
            if (start >= users.size()) {
                out.println("No more users.");
                break;
            }
            out.println("\nListing users (Page " + (page + 1) + "):");
            for (int i = start; i < end; i++) {
                User user = users.get(i);
                out.println("Id: " + (user.getId()) + ", Username: " + user.getUsername() + ", First name: " + user.getFirstName() + ", Last name: " + user.getLastName() + ", Organisation: " + user.getOrganization());
            }
            out.println("Press 'n' for next page or any other key to return: ");
            String option = scanner.nextLine();
            if (!"n".equals(option)) {
                break;
            }
            page++;
        }
    }

  private static void listOrganisations(UserStore userStore, Scanner scanner, PrintStream out) {
        Set<String> uniqueOrganizations = new HashSet<>();
        List<User> users = new ArrayList<>(userStore.getUsers().values());

        for (User user : users) {
            String organisation = user.getOrganization();
            uniqueOrganizations.add(organisation);
        }

        out.println("== All organisations");
        for (String organization : uniqueOrganizations) {
            out.println(organization);
        }
    }


    public static Map<String, List<User>> collectUsersByOrganization(UserStore userStore) {
        Map<String, List<User>> organizationUsersMap = new HashMap<>();
        List<User> users = new ArrayList<>(userStore.getUsers().values());

        for (User user : users) {
            organizationUsersMap.computeIfAbsent(user.getOrganization(), k -> new ArrayList<>()).add(user);
        }

        return organizationUsersMap;
    }

    public static void printUsersByOrganization(UserStore userStore, PrintStream out) {
        Map<String, List<User>> organizationUsersMap = collectUsersByOrganization(userStore);

        out.println("== Organisation Detail");
        organizationUsersMap.forEach((organization, users) -> {
            out.println("Organisation: " + organization);
            users.forEach(user -> out.println("\tUsername: " + user.getUsername()));
        });
    }


    public static void paginateList(List<String> items, int itemsPerPage, PrintStream out, Scanner scanner) {
        int totalItems = items.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            int startItem = (currentPage - 1) * itemsPerPage;
            int endItem = Math.min(startItem + itemsPerPage, totalItems);

            System.out.println("\nPage " + currentPage + " of " + totalPages);
            for (int i = startItem; i < endItem; i++) {
                out.println(items.get(i));
            }

            if (currentPage < totalPages) {
                out.println("\nPress Enter to continue to the next page...");
                scanner.nextLine();
            } else {
                System.out.println("\nEnd of list. Press Enter to return to the previous menu.");
                scanner.nextLine();
                return; // Optionally, return to the previous menu or perform another action
            }
        }
    }

    private static boolean showUserMenu(Scanner scanner, PrintStream out, UserStore userStore) {
        String userMenu = "\n===================================\n1. Auction management\n2. logout\n===================================\nEnter option: ";
        String option = getMenuOption(userMenu, scanner, out);
        switch (option) {
            case "1":
                // Auction management
                boolean showAuctionManagementMenu = true;
                while (true) {
                    showAuctionManagementMenu = showAuctionManagementMenu(scanner, out, userStore);
                }
            case "2":
                // Logout
                out.println("Logging out...");
                break;
            default:
                out.println("Invalid option.");
                break;
        }
        return true;
    }


    private static boolean showAuctionManagementMenu(Scanner scanner, PrintStream out, UserStore userStore) {
        String userManagementMenu = "\n===================================\n1. Create an auction\n2. See your auctions\n3. Close an auction \n4. Bid\n5. Won bids\n6. Lost bids\n7. Go back\n===================================\nEnter option: ";
        String option = getMenuOption(userManagementMenu, scanner, out);
        switch (option) {
            case "1":
                // Create an auction
                break;
            case "2":
                // See your auctions
                break;
            case "3":
                // Close an auction
                break;
            case "4":
                // Bid
                break;
            case "5":
                // Won bids
                break;
            case "6":
                // Lost bids

                break;
            case "7":
                // Go back
                // Will need to know if isAdmin
                showAdminMenu(scanner, out, userStore);
            default:
                out.println("Invalid option.");
                break;
        }
        return true;
    }

}




