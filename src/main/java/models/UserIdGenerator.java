package models;

public class UserIdGenerator {
    private static int nextId = 1;

    public static int generateUserId() {
        return nextId++;
    }

    public static int getNextUserId() {
        return nextId;
    }
}
