package edu.gatech.cs2340_68b.donationtracker.Controllers.Common;

import android.app.AlertDialog;
import android.content.Context;

public class CustomDialog {
    public static AlertDialog.Builder errorDialog(Context context, String tittle, String message) {
        AlertDialog.Builder alert  = new AlertDialog.Builder(context);

        alert.setMessage(message);
        alert.setTitle(tittle);
        alert.setCancelable(true);
        alert.setPositiveButton("OK", null);

        return alert;
    }
}
