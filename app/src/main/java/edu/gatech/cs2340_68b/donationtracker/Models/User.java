package edu.gatech.cs2340_68b.donationtracker.Models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String userID;
    private String username;
    private String password;
    private int failedAttempts;
    private int lastFailed;
    private int lastLogin;
    private boolean isLock;
    private UserType userType;
    private Contact contact;

    public User() {}

    public User(String username, String password) {
        this.userID = java.util.UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

    public boolean getIsLock() { return isLock; }

    public void setIsLock(boolean status) { this.isLock = status; }

    @Override
    public String toString() {
        return username + " " + password + " isLock: " + isLock;
    }
}
