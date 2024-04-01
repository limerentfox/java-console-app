import console.LoginMenu;
import models.AuctionManager;
import models.BusinessException;
import models.User;
import models.UserStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginMenuTest {
    @Mock
    private UserStore userStore;
    @Mock
    private AuctionManager auctionManager;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private LoginMenu loginMenu;
    private Scanner scanner;
    private PrintStream printStream;

    @BeforeEach
    void setUp() {
        printStream = new PrintStream(outContent);
        // Setup dummy input to prevent the test from waiting for user input
        scanner = new Scanner(new ByteArrayInputStream("1\nuser\npass\n2\n".getBytes()));
        loginMenu = new LoginMenu(userStore, scanner, printStream, auctionManager);
    }

    @Test
    void testLoginSuccessAsRegularUser() throws BusinessException {
        // Assuming 'user' is a regular user
        User regularUser = new User(1, "user", "John", "Doe", "pass", "Org", false);
        when(userStore.authenticateUser("user", "pass")).thenReturn(regularUser);

        loginMenu.display();

        String output = outContent.toString();
        assertTrue(output.contains("Welcome John Doe"));
        assertTrue(output.contains("Auction management"));
        assertTrue(output.contains("Logging out..."));
    }

    @Test
    void testLoginSuccessAsAdmin() throws BusinessException {
        // Assuming 'admin' is an admin user
        User adminUser = new User(1, "admin", "Admin", "User", "pass", "Org", true);
        when(userStore.authenticateUser("admin", "pass")).thenReturn(adminUser);

        scanner = new Scanner(new ByteArrayInputStream("1\nadmin\npass\n3\n".getBytes())); // Simulating admin login and logout
        loginMenu = new LoginMenu(userStore, scanner, printStream, auctionManager);

        loginMenu.display();

        String output = outContent.toString();
        assertTrue(output.contains("Welcome Admin User"));
        assertTrue(output.contains("User management"));
        assertTrue(output.contains("Logging out..."));
    }

    @Test
    void testLoginFailure() throws BusinessException {
        when(userStore.authenticateUser("user", "wrong")).thenThrow(new BusinessException("Login failed", BusinessException.ErrorCode.USER_NOT_FOUND));

        scanner = new Scanner(new ByteArrayInputStream("1\nuser\nwrong\n2\n".getBytes())); // Simulating failed login
        loginMenu = new LoginMenu(userStore, scanner, printStream, auctionManager);

        loginMenu.display();

        String output = outContent.toString();
        assertTrue(output.contains("Error logging in: Login failed"));
    }

    @Test
    void testQuit() {
        scanner = new Scanner(new ByteArrayInputStream("2\n".getBytes())); // Simulating choosing to quit
        loginMenu = new LoginMenu(userStore, scanner, printStream, auctionManager);

        loginMenu.display();

        String output = outContent.toString();
        assertTrue(output.contains("Quitting..."));
    }
}
