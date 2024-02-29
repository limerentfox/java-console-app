package models;

public class User {
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String organization;
    private final int id;
    private final Boolean isAdmin;
    private Boolean isBlocked;

    public User (String username, String firstName, String lastName, String password, String organization, int id, Boolean isAdmin, Boolean isBlocked) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.organization = organization;
        this.id = id;
        this.isAdmin = isAdmin;
        this.isBlocked = isBlocked;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return (firstName + " " + lastName);
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean value) {
        isBlocked = value;
    }

    public String getOrganization() {
        return organization;
    }

    public int getId() {
        return id;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }
}
