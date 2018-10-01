package edu.gatech.cs2340_68b.donationtracker.Models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String username = "user";
    private String password = "pass";
    private int failedAttempts;
    private int lastFailed;
    private int lastLogin;
    private boolean isLock;

    public User() {}

    public User(String username, String password, int failedAttempts, int lastFailed, int lastLogin, boolean isLock) {
        this.username = username;
        this.password = password;
        this.failedAttempts = failedAttempts;
        this.lastFailed = lastFailed;
        this.lastLogin = lastLogin;
        this.isLock = isLock;
    }

    public User(String username, String password) {
        this(username, password, 0, 0, 0, false);
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
}
