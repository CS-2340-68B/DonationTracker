package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

import org.apache.commons.validator.routines.EmailValidator;

public class VerifyFormat {
    public static boolean verifyEmailFormat(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
