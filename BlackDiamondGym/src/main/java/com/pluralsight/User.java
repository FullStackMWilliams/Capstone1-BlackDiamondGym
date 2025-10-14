package com.pluralsight;
import java.util.Objects;

public class User {
    private String username;
    private String password;
    private String role;

    public User() { }

    public User(String username, String password, String role) {
        this.username = username.trim();
        this.password = password.trim();
        this.role = role.trim().toUpperCase();
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public void setUsername(String username) {
        this.username = username.trim();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }

    public void setRole(String role) {
        this.role = role.trim().toUpperCase();
    }
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }
    public boolean isMember() {
        return "MEMBER".equalsIgnoreCase(role);
    }
    @Override
    public String toString() {
        return username + " (" + role + ")";
    }
}


