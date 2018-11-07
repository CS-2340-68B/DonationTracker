package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

public class PasswordEncryption {

    public static String encode(String password) {
        String encodedPass = "";
        for (Character each : password.toCharArray()) {
            encodedPass += (char) (((each + 77) * 94) + 33);
        }
        return encodedPass;
    }

    public static String decode(String encodedPassword) {
        String decodedPass = "";
        for (Character each : encodedPassword.toCharArray()) {
            decodedPass += (char) (((each - 33) / 94) - 77);
        }
        return decodedPass;
    }
}
