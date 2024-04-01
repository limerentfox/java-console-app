import models.BusinessException;
import models.User;
import models.UserStore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserStoreTest {
    private UserStore userStore;

    @BeforeAll
    void setup() {
        userStore = new UserStore();
    }

    @BeforeEach
    void resetUserStore() {
        userStore.getAllUsers().clear(); // Clear users before each test
    }

    @Test
    void authenticateUserSuccessfully() throws BusinessException {
        User user = new User(1, "testUser", "Test", "User", "pass123", "Org1", false);
        userStore.addUser(user);
        User authenticatedUser = userStore.authenticateUser("testUser", "pass123");
        assertEquals(user, authenticatedUser);
    }

    @Test
    void authenticateUserFailsWithIncorrectCredentials() {
        User user = new User(1, "testUser", "Test", "User", "pass123", "Org1", false);
        userStore.addUser(user);
        assertThrows(BusinessException.class, () -> userStore.authenticateUser("testUser", "wrongPass"));
    }

    @Test
    void authenticateBlockedUserThrowsException() {
        User user = new User(1, "testUser", "Test", "User", "pass123", "Org1", true);
        userStore.addUser(user);
        BusinessException thrown = assertThrows(BusinessException.class, () -> userStore.authenticateUser("testUser", "pass123"));
        assertEquals(BusinessException.ErrorCode.GENERAL, thrown.getErrorCode());
    }

    @Test
    void findByUsernameSuccessfully() throws BusinessException {
        User user = new User(1, "testUser", "Test", "User", "pass123", "Org1", false);
        userStore.addUser(user);
        User foundUser = userStore.findByUsername("testUser");
        assertEquals(user, foundUser);
    }

    @Test
    void findByUsernameThrowsExceptionWhenUserNotFound() {
        assertThrows(BusinessException.class, () -> userStore.findByUsername("nonexistent"));
    }

    @Test
    void addUserAndRetrieveUniqueOrganisations() {
        userStore.addUser(new User(1, "testUser1", "Test1", "User1", "pass123", "Org1", false));
        userStore.addUser(new User(2, "testUser2", "Test2", "User2", "pass123", "Org2", false));
        Set<String> uniqueOrgs = userStore.getUniqueOrganisations();
        assertTrue(uniqueOrgs.contains("Org1") && uniqueOrgs.contains("Org2") && uniqueOrgs.size() == 2);
    }

    @Test
    void getUsersByOrganisationGroupsCorrectly() {
        userStore.addUser(new User(1, "testUser1", "Test1", "User1", "pass123", "Org1", false));
        userStore.addUser(new User(2, "testUser2", "Test2", "User2", "pass123", "Org1", false));
        Map<String, List<User>> usersByOrg = userStore.getUsersByOrganisation();
        assertEquals(1, usersByOrg.size());
        assertTrue(usersByOrg.containsKey("Org1"));
        assertEquals(2, usersByOrg.get("Org1").size());
    }
}
