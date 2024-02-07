package console;

import java.io.Console;
import java.util.Scanner;

public class LoginMenu {

    public LoginMenu () {

    }

    public void display(MenuContext menuContext) {

    }
    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        Console console = System.console();
        System.out.println("===================================");
        System.out.println("1. Login");
        System.out.println("2. Quit ");
        System.out.println("===================================");
        System.out.println("Enter your option: ");
        int option = userInput.nextInt();




        switch(option)
        {
            case 1:
                String password;
                System.out.println("Enter username: ");
                String username = userInput.nextLine();
                System.out.println("Enter password: ");
                if (console != null) {
                    password = String.valueOf(console.readPassword("Enter password: "));
                } else {
                    password = userInput.nextLine();
                }
                break;
            case 2:
                break;
            default:
                System.out.println("You have entered an invalid option");
                return;
        }


    }
}
