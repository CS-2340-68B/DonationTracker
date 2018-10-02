package edu.gatech.cs2340_68b.donationtracker.Models;

public class TempDataBase {
    public static User tempUser;

    // Default constructor hardcoded for now
    public  TempDataBase() {
        this.tempUser = new User("user@gmail.com", "pass");
    }

    // Getter for temp user
    public User getTempUser() {
        return tempUser;
    }
}