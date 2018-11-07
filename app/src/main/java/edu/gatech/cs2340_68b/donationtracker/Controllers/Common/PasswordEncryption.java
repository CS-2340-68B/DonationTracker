package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

public class PasswordEncryption {
    static final int NUM_77 = 77;
    static final int NUM_94 = 94;
    static final int NUM_33 = 33;



    public static String encode(String password) {
        String encodedPass = "";
        for (Character each : password.toCharArray()) {
            encodedPass += (char) (((each + NUM_77) * NUM_94) + NUM_33);
        }
        return encodedPass;
    }

    public static String decode(String encodedPassword) {
        String decodedPass = "";
        for (Character each : encodedPassword.toCharArray()) {
            decodedPass += (char) (((each - NUM_33) / NUM_94) - NUM_77);
        }
        return decodedPass;
    }
}
