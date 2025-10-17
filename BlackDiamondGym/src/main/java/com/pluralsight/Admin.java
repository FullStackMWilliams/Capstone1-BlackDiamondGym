package com.pluralsight;

/**
 * The Admin class represents an administrator user.
 * It inherits from the User class and automatically sets the role to "ADMIN".
 * Admin users will have special privileges like viewing sales, ledger data, and managing transactions.
 */
public class Admin extends User {

    /**
     * Default constructor.
     * This will create an Admin user with no username or password set yet.
     * Useful when you plan to set them later using setters.
     */
    public Admin() {
        super();               // Call the default User() constructor
        setRole("ADMIN");      // Ensure that the role is always ADMIN
    }

    /*
     * Parameterized constructor.
     * Creates a new Admin with a username and password.
     *
     * @param username The admin's username.
     * @param password The admin's password.
     */
    public Admin(String username, String password) {
        super(username, password, "ADMIN"); // Calls User constructor and sets role to ADMIN
    }

}
