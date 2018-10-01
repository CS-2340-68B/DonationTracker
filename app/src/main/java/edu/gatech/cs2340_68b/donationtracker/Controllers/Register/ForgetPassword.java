package edu.gatech.cs2340_68b.donationtracker.Controllers.Register;

import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat;


import edu.gatech.cs2340_68b.donationtracker.Controllers.Login.Login;
import edu.gatech.cs2340_68b.donationtracker.R;


public class ForgetPassword extends AppCompatActivity {
    private Button resetButton;
    private Button goBack;
    private TextInputEditText userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        resetButton = (Button)findViewById(R.id.resetButton);
        goBack = (Button)findViewById(R.id.goBack);
        userEmail = findViewById(R.id.emailUser);

        // Reset button click
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = userEmail.getText().toString().trim();
                if (inputEmail.length() != 0 && VerifyFormat.verifyEmailFormat(inputEmail)) {
                    // Move to reset page
                    Intent intent = new Intent(ForgetPassword.this, resetPassword.class);
//                    intent.putExtra(inputEmail, )
                    startActivity(intent);
                } else {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(ForgetPassword.this,
                            "Format Error", "Email format is not correct");
                    alert.create().show();
                    return;
                }
            }
        });

        // Go back click
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}