package console;

import models.User;
import models.UserIdGenerator;
import models.UserStore;

import java.io.PrintStream;
import java.util.*;

public class UserManagementMenu {
    private final Scanner scanner;
    private final PrintStream out;
    private final UserStore userStore;
    private User currentUser;

    public UserManagementMenu(Scanner scanner, PrintStream out, UserStore userStore) {
        this.scanner = scanner;
        this.out = out;
        this.userStore = userStore;
    }

    public void displayUserManagementMenu() {
        boolean running = true;
        while (running) {
            out.println("\n=== User Management Menu ===");
            out.println("1. Create user");
            out.println("2. Block user");
            out.println("3. Unblock user");
            out.println("4. Show users");
            out.println("5. Show organisations");
            out.println("6. Show organisation details");
            out.println("7. Go back");
            out.print("Enter option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    createUser();
                    break;
                case "2":
                    blockUser();
                    break;
                case "3":
                    unblockUser();
                    break;
                case "4":
                    showUsers();
                    break;
                case "5":
                    showOrganisations();
                    break;
                case "6":
                    showOrganisationDetails();
                    break;
                case "7":
                    running = false;
                    break;
                default:
                    out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void createUser() {
        out.print("Enter username: ");
        String username = scanner.nextLine();
        out.print("Enter password: ");
        String password = scanner.nextLine();
        out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        out.print("Enter organisation: ");
        String organisation = scanner.nextLine();
        int id = UserIdGenerator.generateUserId();
        User newUser = new User(id, username, firstName, lastName, password, organisation);
        userStore.addUser(newUser); // Ensure addUser method exists and functions as expected
        out.println("User created successfully.");
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private void blockUser() {
        out.print("Enter username of user to block: ");
        String username = scanner.nextLine();
        currentUser.setIsBlocked(true);
        out.println("User blocked successfully.");
    }

    private void unblockUser() {
        out.print("Enter username of user to unblock: ");
        String username = scanner.nextLine();
        currentUser.setIsBlocked(false);
        out.println("User unblocked successfully.");
    }


    private void showUsers() {
        List<User> users = new ArrayList<>(userStore.getAllUsers().values());
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

    private void showOrganisations() {
        Set<String> uniqueOrganizations = userStore.getUniqueOrganisations();
        out.println("== All organisations");
        for (String organization : uniqueOrganizations) {
            out.println(organization);
        }
    }

    private void showOrganisationDetails() {
        Map<String, List<User>> usersByOrg = userStore.getUsersByOrganisation();
        out.println("== Organisation Detail");
        usersByOrg.forEach((organization, users) -> {
            out.println("Organisation: " + organization);
            users.forEach(user -> out.println("\tUsername: " + user.getUsername()));
        });
    }

}
