package console;

import java.util.Scanner;

public class ConsoleAuction {
    private final MenuContext menuContext;

    public ConsoleAuction() {
        Scanner scanner = new Scanner(System.in);
        menuContext = new MenuContext(scanner);
    }

    public void start () {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.display(menuContext);
    }

}
