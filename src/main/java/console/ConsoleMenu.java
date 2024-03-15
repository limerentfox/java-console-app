package console;

public class ConsoleMenu {
    private static int nextId = 1;

    public static int generateUserId() {
        return nextId++;
    }

    public static int getNextUserId() {
        return nextId;
    }

    public static void resetIdGenerator() {
        nextId = 1;
    }





}
