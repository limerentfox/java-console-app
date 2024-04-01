package models;

import java.util.*;

public class UserStore {
    private static final Map<Integer, User> users = new HashMap<>();

    public User authenticateUser(String username, String password) throws BusinessException {
        User currentUser = null;
        for (User user : users.values()) {
            if (validateUsername(user, username) && validatePassword(user, password)) {
                currentUser = user;
            } else {
                throw new BusinessException("Username or password does not match our records. Please try again.", BusinessException.ErrorCode.USER_NOT_FOUND);
            }

            if (!validateUserIsNotBlocked(user, user.getIsBlocked())) {
                return currentUser;
            }
        }

        throw new BusinessException("User is showing as blocked in our records. Please contact your admin for assistance.", BusinessException.ErrorCode.GENERAL);
    }

    public User findByUsername(String username) throws BusinessException {
        for (User user : users.values()) {
            if (this.validateUsername(user, username)) {
                return user;
            }
        }

        throw new BusinessException("No user exists in our records with that username. Please try again.", BusinessException.ErrorCode.USER_NOT_FOUND);
    }

    public boolean validateUserIsNotBlocked(User user, boolean isBlocked) { return user.getIsBlocked().equals(isBlocked); }
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
