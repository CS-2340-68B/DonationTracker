package edu.gatech.cs2340_68b.donationtracker.Models;

import java.io.Serializable;
import android.support.annotation.NonNull;
import java.util.ArrayList;

import edu.gatech.cs2340_68b.donationtracker.Models.Enum.UserType;

/***
 * User class define a specific user, each have an account with username
 * and password associate to them. Different types of account have different
 * access to each part of the app.
 */
public class User implements Serializable {
    private String userID;
    private String username = "user@gmail.com";
    private String password = "pass";
    private int failedAttempts;
    private int lastFailed;
    private int lastLogin;
    private UserType type;
    private boolean isLock;
    private Contact contact;
    private String assignedLocation;

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    private String userKey;
    private ArrayList<UserSearch> userSearchList;

    /**
     * Get a user search list
     * @return user list
     */
    public ArrayList<UserSearch> getUserSearchList() {
        return userSearchList;
    }

    // For the purpose of create instance from Firebase

    /***
     * Base constructor to create User instance
     */
    public User() {
    }

    /***
     * Create new user from current user (Copy)
     *
     * @param newuser current user to copy
     */
    public User(User newuser) {
        this.userID = newuser.userID;
        this.username = newuser.username;
        this.password = newuser.password;
        this.failedAttempts = newuser.failedAttempts;
        this.lastFailed = newuser.lastFailed;
        this.lastLogin = newuser.lastLogin;
        this.type = newuser.type;
        this.isLock = newuser.isLock;
        this.contact = newuser.contact;
        this.assignedLocation = newuser.assignedLocation;
        this.userSearchList = newuser.userSearchList;
        this.userKey = newuser.userKey;
    }

    /***
     * Constructor to create new user with all necessary information
     *
     * @param username account's username
     * @param password account's password
     * @param failedAttempts number of failed attempts
     * @param type user type
     * @param assignedLocation user's assigned location.
     */
    public User(String username, String password, int failedAttempts,
                UserType type, String assignedLocation) {
        this.username = username;
        this.password = password;
        this.failedAttempts = failedAttempts;
        this.type = type;
        this.assignedLocation = assignedLocation;
    }

    /***
     * Constructor for creating new user with username and password
     *
     * @param username user's account username
     * @param password user's account password
     */
    public User(String username, String password) {
        //this.userID = java.util.UUID.randomUUID().toString();
        this(username, password, 0, UserType.USER, null);
    }

    /***
     * Getter for the user id
     *
     * @return user id
     */
    public String getUserID() {
        return userID;
    }

    /***
     * Setter for user id
     *
     * @param userID new user id
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /***
     * Getter for the user's contact
     *
     * @return user's contact
     */
    public Contact getContact() {
        return contact;
    }

    /***
     * Setter for user's contact
     *
     * @param contact new contact info
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /***
     * Getter for username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /***
     * Setter for the username
     *
     * @param username new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /***
     * Getter for password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /***
     * Setter for password
     *
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /***
     * Getter for the count of failed attempt
     *
     * @return count of failed attempt
     */
    public int getFailedAttempts() {
        return failedAttempts;
    }

    /***
     * Setter for the count of failed attempts
     *
     * @param failedAttempts number of current failed attempts
     */
    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    /***
     * Increase failed attempt by one
     */
    public void incrementFailed() {
        this.failedAttempts++;
    }

    /***
     * Getter for the user type
     *
     * @return user type
     */
    public UserType getType() {
        return this.type;
    }

    /***
     * Setter for user type
     *
     * @param type user's type
     */
    public void setType(UserType type) {
        this.type = type;
    }

    /***
     * Getter for the lock status
     *
     * @return lock status
     */
    public boolean getIsLock() {
        return isLock;
    }

    /***
     * Setter for lock status
     *
     * @param status current lock status
     */
    public void setIsLock(boolean status) {
        this.isLock = status;
    }

    /***
     * Setter for user's assigned location
     *
     * @param assignedLocation new user's assigned location
     */
    public void setAssignedLocation(String assignedLocation) {
        this.assignedLocation = assignedLocation;
    }

    /***
     * Getter for the user's assigned location
     *
     * @return current user's assigned location
     */
    public String getAssignedLocation() {
        return this.assignedLocation;
    }

    @NonNull
    @Override
    public String toString() {
        return username + " " + password +
                " isLock: " + isLock + ", userKey: " + userKey;
    }
}
