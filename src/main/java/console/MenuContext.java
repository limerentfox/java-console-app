package console;

import java.util.Scanner;

public class MenuContext {
    private final Scanner scanner;
    public MenuContext(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner () {
        return scanner;
    }

}
