import models.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class UserIdGeneratorTest {

    @BeforeEach
    @AfterEach
    void resetUserIdGeneratorState() throws Exception {
        Field field = UserIdGenerator.class.getDeclaredField("nextId");
        field.setAccessible(true);
        field.setInt(null, 1);
    }

    @Test
    void generateUserIdShouldIncrementId() {
        int firstId = UserIdGenerator.generateUserId();
        int secondId = UserIdGenerator.generateUserId();

        assertEquals(1, firstId, "The first generated ID should be 1.");
        assertEquals(2, secondId, "The second generated ID should be 2.");
    }

    @Test
    void getNextUserIdShouldReturnNextIdWithoutIncrementing() {
        int initialNextId = UserIdGenerator.getNextUserId();

        UserIdGenerator.generateUserId();

        int nextIdAfterGeneration = UserIdGenerator.getNextUserId();

        assertEquals(1, initialNextId, "Initially, the next ID should be 1.");
        assertEquals(2, nextIdAfterGeneration, "After one generation, the next ID should be 2.");
    }
}
