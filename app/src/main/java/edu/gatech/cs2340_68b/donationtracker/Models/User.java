package edu.gatech.cs2340_68b.donationtracker.Models;

import edu.gatech.cs2340_68b.donationtracker.Models.Enum.UserType;

public class User {
    private String userID;
    private String username = "user@gmail.com";
    private String password = "pass";
    private int failedAttempts;
    private int lastFailed;
    private int lastLogin;
    private UserType type;
    private boolean isLock;
    private UserType userType;
    private Contact contact;
    private String assignedLocation;

    // For the purpose of create instance from Firebase
    public User() {
    }

    public User(String username, String password, int failedAttempts, int lastFailed,
                int lastLogin, UserType type, String assignedLocation) {
        this.username = username;
        this.password = password;
        this.failedAttempts = failedAttempts;
        this.lastFailed = lastFailed;
        this.lastLogin = lastLogin;
        this.type = type;
        this.assignedLocation = assignedLocation;
    }

    public User(String username, String password) {
        //this.userID = java.util.UUID.randomUUID().toString();
        this(username, password, 0, 0, 0, UserType.USER, null);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public void incrementFailed() {
        this.failedAttempts++;
    }

    public int getLastFailed() {
        return lastFailed;
    }

    public void setLastFailed(int lastFailed) {
        this.lastFailed = lastFailed;
    }

    public int getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(int lastLogin) {
        this.lastLogin = lastLogin;
    }

    public UserType getType() {
        return this.type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public boolean getIsLock() {
        return isLock;
    }

    public void setIsLock(boolean status) {
        this.isLock = status;
    }

    public void setAssignedLocation(String assignedLocation) {
        this.assignedLocation = assignedLocation;
    }

    public String getAssignedLocation() {
        return this.assignedLocation;
    }

    @Override
    public String toString() {
        return username + " " + password + " isLock: " + isLock;
    }
}
