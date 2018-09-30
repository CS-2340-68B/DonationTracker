package edu.gatech.cs2340_68b.donationtracker.Controllers.Register;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Login.Login;
import edu.gatech.cs2340_68b.donationtracker.R;

public class Register extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private TextView confirmPassword;
    private Button register;
    private Button cancel;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));

        username = (TextView)findViewById(R.id.registerUsername);
        password = (TextView)findViewById(R.id.registerPassword);
        confirmPassword = (TextView)findViewById(R.id.registerConfirmPassword);
        register = (Button)findViewById(R.id.register);
        cancel = (Button)findViewById(R.id.cancel);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VerifyFormat.verifyEmailFormat(username.getText().toString())) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Error", "User account format is not correct.");
                    alert.create().show();
                    return;
                }
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Error", "Password are not the same.");
                    alert.create().show();
                    return;
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
