package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

import org.apache.commons.validator.routines.EmailValidator;

@SuppressWarnings("OverlyComplexMethod")
public class VerifyFormat {
    public static boolean verifyEmailFormat(String email) {
        return EmailValidator.getInstance().isValid(email);
    }


    public static boolean verifyPassword (String pass) {
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

