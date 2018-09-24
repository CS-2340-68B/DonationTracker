package edu.gatech.cs2340_68b.donationtracker.Models;

public class AccountAttempt {
    private String email;
    private int attempt;

    public AccountAttempt(){

    }

    public AccountAttempt(String email, int attempt) {
        this.email = email;
        this.attempt = attempt;
    }

    public int getAttempts() {
        return attempt;
    }

    public void setAttempts(int attempt) {
        this.attempt = attempt;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
