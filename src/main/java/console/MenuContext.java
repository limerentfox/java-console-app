package console;

import models.User;

import java.io.PrintStream;
import java.util.Scanner;

public class MenuContext {
    private final Scanner scanner;
    private final PrintStream out;
    private User currentUser;
    public MenuContext(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public Scanner getScanner () {
        return scanner;
    }

    public PrintStream getOut() {
        return out;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public User getCurrentUser() {
       return currentUser;
    }
}
