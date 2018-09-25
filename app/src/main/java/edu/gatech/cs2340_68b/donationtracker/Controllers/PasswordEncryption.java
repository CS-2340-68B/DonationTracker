package edu.gatech.cs2340_68b.donationtracker.Controllers;

public class PasswordEncryption {
    private String password;
    private String encoded;

    public PasswordEncryption(String password) {
        this.password = password;
    }

    public String encode() {
        String encodedPass = "";
        for (Character each : password.toCharArray()) {
            encodedPass += (char) (each + 1);
        }
        encoded = encodedPass;
        return encodedPass;
    }

    public String decode() {
        String decodedPass = "";
        for (Character each : encoded.toCharArray()) {
            decodedPass += (char) (each - 1);
        }
        return decodedPass;
    }
}
