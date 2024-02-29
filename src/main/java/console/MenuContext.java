package console;

import models.User;
import models.UserStore;

import java.io.PrintStream;
import java.util.Scanner;

public class MenuContext {
    private final Scanner scanner;
    private final PrintStream out;
    private final UserStore userStore;
    private User currentUser;
    public MenuContext(UserStore userStore, Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
        this.userStore = userStore;
    }

    public PrintStream getOut() {
        return out;
    }

    public Scanner getScanner() { return scanner;}

    public UserStore getUserStore() {
        return userStore;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public User getCurrentUser() {
       return currentUser;
    }
}
