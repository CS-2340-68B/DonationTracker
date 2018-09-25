package edu.gatech.cs2340_68b.donationtracker.Controllers;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Models.TempDataBase;
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
                String inputUsername = username.getText().toString();
                String inputPassword = password.getText().toString();

                if (inputUsername.equals(Welcome.tempDB.getTempUser().getUsername())) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Registration Error", "Username already exists");
                    alert.create().show();
                    return;
                } else {
                    Welcome.currentUser.setUsername(inputUsername);
                    Welcome.currentUser.setPassword(inputPassword);
                    Welcome.tempDB.getTempUser().setUsername(inputUsername);
                    Welcome.tempDB.getTempUser().setPassword(inputPassword);
                }
                Intent intent = new Intent(Register.this, MainPage.class);
                startActivity(intent);

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
