import console.UserManagementMenu;
import models.BusinessException;
import models.User;
import models.UserStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserManagementMenuTest {
    @Mock
    private UserStore userStore;

    private ByteArrayOutputStream outContent;
    private UserManagementMenu userManagementMenu;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent)); // Redirect System.out to capture outputs
    }

    void setupInput(String data) {
        Scanner scanner = new Scanner(data);
        userManagementMenu = new UserManagementMenu(scanner, System.out, userStore);
    }

    @Test
    void testCreateUserSuccessfully() {
        setupInput("1\nuser1\npassword\nJohn\nDoe\nOrg\n7\n"); // Simulates creating a user and then exiting
        doNothing().when(userStore).addUser(any(User.class));

        userManagementMenu.displayUserManagementMenu();

        assertTrue(outContent.toString().contains("User created successfully."));
        verify(userStore, times(1)).addUser(any(User.class));
    }

    @Test
    void testBlockUserSuccessfully() throws BusinessException {
        setupInput("2\nuser1\n7\n"); // Simulates blocking a user and then exiting
        User mockUser = mock(User.class);
        when(userStore.findByUsername("user1")).thenReturn(mockUser);

        userManagementMenu.displayUserManagementMenu();

        assertTrue(outContent.toString().contains("User blocked successfully."));
        verify(mockUser, times(1)).setIsBlocked(true);
    }

    @Test
    void testShowUsers() {
        setupInput("4\nn\n7\n"); // Simulates viewing users, going to next page, and then exiting
        when(userStore.getAllUsers()).thenReturn(Map.of(1, new User(1, "user1", "John", "Doe", "password", "Org", false)));

        userManagementMenu.displayUserManagementMenu();

        assertTrue(outContent.toString().contains("Listing users (Page 1):"));
        assertTrue(outContent.toString().contains("user1"));
    }

    @Test
    void testShowOrganisations() {
        setupInput("5\n7\n"); // Simulates showing organisations and then exiting
        when(userStore.getUniqueOrganisations()).thenReturn(Set.of("Org1", "Org2"));

        userManagementMenu.displayUserManagementMenu();

        assertTrue(outContent.toString().contains("== All organisations"));
        assertTrue(outContent.toString().contains("Org1"));
        assertTrue(outContent.toString().contains("Org2"));
    }

    @Test
    void testUnblockUserSuccessfully() throws BusinessException {
        setupInput("3\nuser1\n7\n"); // Simulates unblocking a user and then exiting
        User mockUser = mock(User.class);
        when(userStore.findByUsername("user1")).thenReturn(mockUser);

        userManagementMenu.displayUserManagementMenu();

        assertTrue(outContent.toString().contains("User unblocked successfully."));
        verify(mockUser, times(1)).setIsBlocked(false);
    }

    @Test
    void testShowOrganisationDetails() {
        setupInput("6\n7\n"); // Simulates showing organisation details and then exiting
        User user1 = new User(1, "user1", "John", "Doe", "password", "Org1", false);
        User user2 = new User(2, "user2", "Jane", "Doe", "password", "Org2", false);
        when(userStore.getUsersByOrganisation()).thenReturn(Map.of(
                "Org1", List.of(user1),
                "Org2", List.of(user2)
        ));

        userManagementMenu.displayUserManagementMenu();

        assertTrue(outContent.toString().contains("== Organisation Detail"));
        assertTrue(outContent.toString().contains("Organisation: Org1"));
        assertTrue(outContent.toString().contains("Organisation: Org2"));
    }

    @Test
    void testInvalidOption() {
        setupInput("8\n7\n"); // Simulates entering an invalid option and then exiting

        userManagementMenu.displayUserManagementMenu();

        assertTrue(outContent.toString().contains("Invalid option. Please try again."));
    }

}
