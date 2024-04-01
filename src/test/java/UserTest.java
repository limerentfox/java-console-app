import models.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void userInitializationAndGetterTests() {
        User user = new User(1, "testUser1", "John", "Doe", "password123", "exampleOrg", false);

        assertEquals("testUser1", user.getUsername());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("John Doe", user.getFullName());
        assertEquals("password123", user.getPassword());
        assertEquals("exampleOrg", user.getOrganization());
        assertEquals(1, user.getId());
        assertFalse(user.getIsAdmin());
        assertFalse(user.getIsBlocked());
    }

    @Test
    void isAdminBasedOnOrganization() {
        User adminUser = new User(2, "adminUser", "Admin", "User", "adminPass", "admin", true);
        assertTrue(adminUser.getIsAdmin());
    }

    @Test
    void blockingAndUnblockingUser() {
        User user = new User(3, "testUser", "John", "Smith", "password", "userOrg", false);

        assertFalse(user.getIsBlocked(), "Initially, user should not be blocked.");

        user.setIsBlocked(true);
        assertTrue(user.getIsBlocked(), "User should be blocked after calling setIsBlocked(true).");

        user.setIsBlocked(false);
        assertFalse(user.getIsBlocked(), "User should be unblocked after calling setIsBlocked(false).");
    }
}
