package models;

public class User {
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String organisation;
    private final int id;
    private final Boolean isAdmin;
    private Boolean isBlocked;

    public User (int id, String username, String firstName, String lastName, String password, String organisation, boolean isAdmin) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.organisation = organisation;
        this.id = id;
        this.isAdmin = isAdmin;
        this.isBlocked = false;
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

    public String getOrganization() {
        return organisation;
    }

    public int getId() {
        return id;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsBlocked(boolean status) {
        this.isBlocked = status;
    }
}
