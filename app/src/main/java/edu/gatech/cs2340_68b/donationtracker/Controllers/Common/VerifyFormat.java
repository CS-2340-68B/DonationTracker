package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Verify the email and password format of the account
 */
@SuppressWarnings({"OverlyComplexMethod", "UtilityClass", "ChainedMethodCall"})
public class VerifyFormat {
    /**
     * Verifies an email format
     * @param email user email
     * @return a boolean if email format is valid
     */
    public static boolean verifyEmailFormat(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    /**
     * Verifies a password format
     * @param pass a new password
     * @return if a new password format is valid
     */
    public static boolean verifyPassword (CharSequence pass) {
        if ((pass == null) || (pass.length() == 0)) {
            return false;
        }

        char character;
        boolean capitalFlag = false;
        boolean numberFlag = false;
        boolean letterFlag = false;
        boolean lengthFlag = false;
        boolean specialFlag = false;

        if (pass.length() >= 8) {
            lengthFlag = true;
        }

        for (int i = 0; i < pass.length(); i++) {
            character = pass.charAt(i);
            if (Character.isUpperCase(character)) {capitalFlag = true;}
            if (Character.isDigit(character)) {numberFlag = true;}
            if (Character.isUpperCase(character) || Character.isLowerCase(character)) {
                letterFlag = true;
            }
            Character c = character;
            if (c.toString().matches("[^a-z A-Z0-9]")) {
                specialFlag = true;
            }
        }

        return (capitalFlag && numberFlag && letterFlag && lengthFlag && !specialFlag);

    }
}

