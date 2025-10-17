package com.pluralsight;

import java.util.Objects;

/*
  The User class represents a single user account in the BlackDiamondGym system.
  It stores basic login details (username and password) and a role that defines
  the user's permissions (e.g., ADMIN or MEMBER).
 */
public class User {

    // === Fields (variables that hold data about a user) ===
    private String username;  // The user's login name
    private String password;  // The user's password
    private String role;      // The user's role (e.g., ADMIN, MEMBER)

    // === Constructors ===

    /*
      Default constructor (no arguments).
      Useful when creating a blank user and setting fields later with setters.
     */
    public User() { }

    /*
      Constructor used when reading a user with username and password ONLY.
      This is now added to fix the FileManager readUsers() error.
      It defaults the role to "MEMBER".
     */
    public User(String username, String password) {
        this.username = username.trim();
        this.password = password.trim();
        this.role = "MEMBER"; // Default role if none provided
    }

    /*
      Full constructor - allows setting username, password, and role.
      This is useful when creating users from admin panel or importing data.
     */
    public User(String username, String password, String role) {
        this.username = username.trim();
        this.password = password.trim();
        this.role = role.trim().toUpperCase();
    }

    // === Getters (used to access private fields) ===


     // @return the username for this user

    public String getUsername() {
        return username;
    }


     // return the password for this user

    public String getPassword() {
        return password;
    }


     // @return the role (e.g., ADMIN or MEMBER)

    public String getRole() {
        return role;
    }

    // === Setters (used to update private fields) ===


     // Sets the username. Leading/trailing spaces are removed.

    public void setUsername(String username) {
        this.username = username.trim();
    }


     // Sets the password. Leading/trailing spaces are removed.

    public void setPassword(String password) {
        this.password = password.trim();
    }


     // Sets the role. It’s automatically converted to uppercase.

    public void setRole(String role) {
        this.role = role.trim().toUpperCase();
    }

    // === Utility Methods ===


     // @return true if this user is an ADMIN

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }


     // @return true if this user is a MEMBER

    public boolean isMember() {
        return "MEMBER".equalsIgnoreCase(role);
    }

    /*
      Creates a simple text representation of a user.
      Useful for debugging or printing user info in logs.
     */
    @Override
    public String toString() {
        return username + " (" + role + ")";
    }

    /*
      Ensures two User objects are considered equal if their username is the same.
      (Optional but recommended for comparisons, especially in lists.)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

