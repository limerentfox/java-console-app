package models;

import java.util.*;

public class UserStore {
    private static final Map<Integer, User> users = new HashMap<>();

    public User authenticateUser(String username, String password) {

        for (User user : users.values()) {
            if (this.validateUsername(user, username) && this.validatePassword(user, password)) {
                return user;
            }
        }

        return null;
    }

    public User findByUsername(String username) {
        for (User user : users.values()) {
            if (this.validateUsername(user, username)) {
                return user;
            }
        }

        return null;
    }

    public boolean validateUsername(User user, String username) {
        return user.getUsername().equals(username);
    }

    public boolean validatePassword(User user, String password) {
        return user.getPassword().equals(password);
    }
    public Map<Integer, User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void removeUser(int userId) {
        users.remove(userId);
    }


    public Set<String> getUniqueOrganisations() {
        Set<String> uniqueOrganizations = new HashSet<>();
        List<User> users = new ArrayList<>(this.getAllUsers().values());

        for (User user : users) {
            String organisation = user.getOrganization();
            uniqueOrganizations.add(organisation);
        }

       return uniqueOrganizations;
    }

   public Map<String, List<User>> getUsersByOrganisation() {
        Map<String, List<User>> organizationUsersMap = new HashMap<>();
        List<User> users = new ArrayList<>(this.getAllUsers().values());

        for (User user : users) {
            organizationUsersMap.computeIfAbsent(user.getOrganization(), k -> new ArrayList<>()).add(user);
        }

        return organizationUsersMap;
    }
}
