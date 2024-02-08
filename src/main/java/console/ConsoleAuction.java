package console;


import models.User;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleAuction {
    private final MenuContext menuContext;

    public ConsoleAuction() {
        Scanner scanner = new Scanner(System.in);
        PrintStream out =  System.out;

        menuContext = new MenuContext(scanner, out);
    }

    public void start () {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.display(menuContext);
    }

}
