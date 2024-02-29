package models;

import java.util.HashMap;
import java.util.Map;

public class UserStore {
    private static Map<Integer, User> users = new HashMap<>();

    public User getUser(Map<Integer, User> users, String username, String password) {
        for (User user : users.values()) {
            if (this.validateUsername(user, username) && this.validatePassword(user, password)) {
                return user;
            }
        }

        return null; // Return null if user with given username is not found
    }

    public User findByUsername(Map<Integer, User> users, String username) {
        for (User user : users.values()) {
            if (this.validateUsername(user, username)) {
                return user;
            }
        }

        return null; // Return null if user with given username is not found
    }



    public boolean validateUsername(User user, String username) {
        return user.getUsername().equals(username);
    }

    public boolean validatePassword(User user, String password) {
        return user.getPassword().equals(password);
    }
    public Map<Integer, User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void removeUser(int userId) {
        users.remove(userId);
    }


}
