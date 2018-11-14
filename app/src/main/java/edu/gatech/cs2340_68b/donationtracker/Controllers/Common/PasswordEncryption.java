package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

/**
 * Encrypt a password to database
 */
@SuppressWarnings("UtilityClass")
public class PasswordEncryption {
    private static final int NUM_77 = 77;
    private static final int NUM_94 = 94;
    private static final int NUM_33 = 33;


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
        StringBuilder decodedPass = new StringBuilder();
        for (Character each : encodedPassword.toCharArray()) {
            decodedPass.append((char) (((each - NUM_33) / NUM_94) - NUM_77));
        }
        return decodedPass.toString();
    }
}
