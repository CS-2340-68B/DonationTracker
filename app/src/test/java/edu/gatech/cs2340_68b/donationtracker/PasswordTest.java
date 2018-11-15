package edu.gatech.cs2340_68b.donationtracker;

import org.junit.Before;
import org.junit.Test;

import static edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat.verifyPassword;
import static org.junit.Assert.*;

/**
 * Tests the verifyPassword(String pass) method
 * in the VerifyFormat class
 *
 * @author Chanhee Lee
 */
@SuppressWarnings("FeatureEnvy")
public class PasswordTest {
    private String correctPass;

    @Before
    public void setUp() {
        correctPass = "Password123";

//        boolean capitalFlag = false;
//        boolean numberFlag = false;
//        boolean letterFlag = false;
//        boolean lengthFlag = false;

    }

    @Test (timeout = 200)
    public void testInvalid(){
        String emptyPass = "";

        assertFalse(verifyPassword(emptyPass));
        assertFalse(verifyPassword(null));
    }

    @Test (timeout = 200)
    public void testIncorrect() {
        // Capital Flag is false
        String capString = "password123";
        assertFalse(verifyPassword(capString));

        // Number Flag is false
        String numberString = "Password";
        assertFalse(verifyPassword(numberString));

        // Letter Flag is false
        String letterString = "94550983";
        assertFalse(verifyPassword(letterString));

        // Length Flag is false
        String[] specialChar = {"!", "@", "#", "%", "^",
        "&", "*", "(", "-", "+", "\\", "\n"};
        for (String str : specialChar) {
            String specialString1 = correctPass + str;
            String specialString2 = str + correctPass;
            assertFalse(verifyPassword(specialString1));
            assertFalse(verifyPassword(specialString2));
        }
    }

    @Test (timeout = 200)
    public void testCorrect() {
        assertTrue(verifyPassword(correctPass));

        // All Flags True
        String pass1 = "PPPPPPPP0";
        String pass2 = "00000000P";
        String pass3 = "PASS1PASS";
        String pass4 = "Onecapitalletteranda"+
                "reallylongpasswordwithanumber0";

        assertTrue(verifyPassword(pass1));
        assertTrue(verifyPassword(pass2));
        assertTrue(verifyPassword(pass3));
        assertTrue(verifyPassword(pass4));

    }
}
