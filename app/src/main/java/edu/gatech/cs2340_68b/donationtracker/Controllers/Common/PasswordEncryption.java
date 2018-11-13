package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

/**
 * Encrypt a password to database
 */
public class PasswordEncryption {
    static final int NUM_77 = 77;
    static final int NUM_94 = 94;
    static final int NUM_33 = 33;


    /**
     * Encode a password
     * @param password a new password
     * @return encrypted password
     */
    public static String encode(String password) {
        StringBuilder encodedPass = new StringBuilder();
        for (Character each : password.toCharArray()) {
            encodedPass.append((char) (((each + NUM_77) * NUM_94) + NUM_33));
        }
        return encodedPass.toString();
    }

    /**
     *
     * Decode a password
     * @param encodedPassword decoded password
     * @return a decoded password
     * @throws IllegalArgumentException throw when pass in null String
     */
    public static String decode(String encodedPassword) {
        if (encodedPassword == null) {
            throw new IllegalArgumentException(
                    "Encode String cannot be null");
        }
        String decodedPass = "";
        for (Character each : encodedPassword.toCharArray()) {
            decodedPass += (char) (((each - NUM_33) / NUM_94) - NUM_77);
        }
        return decodedPass;
    }
}
