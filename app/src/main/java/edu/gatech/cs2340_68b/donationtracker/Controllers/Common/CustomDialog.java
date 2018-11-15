package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Sets up custom dialog
 */
@SuppressWarnings("UtilityClass")
public class CustomDialog {
    /** Sets up the error
     *
     * @param context context
     * @param title title of alert
     * @param message message for error
     * @return returns custom dialog with error message
     */
    public static AlertDialog.Builder errorDialog(
            Context context, CharSequence title,
            CharSequence message) {
        AlertDialog.Builder alert  = new AlertDialog.Builder(context);

        alert.setMessage(message);
        alert.setTitle(title);
        alert.setCancelable(true);
        alert.setPositiveButton("OK", null);

        return alert;
    }
}
