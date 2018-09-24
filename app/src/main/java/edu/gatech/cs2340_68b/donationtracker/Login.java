package edu.gatech.cs2340_68b.donationtracker;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private TextView forgetPassword;
    private Button login;
    private Button cancel;
    public User currentUser;
    private int loginClick = 0;
    private ActionBar actionBar;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (TextView)findViewById(R.id.registerUsername);
        password = (TextView)findViewById(R.id.registerPassword);
        cancel = (Button)findViewById(R.id.cancel);
        login = (Button)findViewById(R.id.login);
        forgetPassword = (TextView)findViewById(R.id.forgetPassword);
//        ConstraintLayout current = (ConstraintLayout) findViewById(R.id.layout);
//        current.setBackgroundColor(Color.WHITE);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Firebase sample
//                DatabaseReference myRef = database.getReference();
//                myRef.setValue("Tuan");
//                Intent intent = new Intent(Login.this, Welcome.class);
//                startActivity(intent);
                finish();
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginClick++ == 0) {
                    currentUser = new User(username.getText().toString(),
                            password.getText().toString());
                }

                // When verified, move to main page
                if (currentUser.getUsername().equals("user") &&
                        currentUser.getPassword().equals("pass")) {
                    Intent intent = new Intent(Login.this, MainPage.class);
                    startActivity(intent);
                } //Basic implementation of account lock out
                else if (currentUser.getUsername().equals("user") &&
                        !currentUser.getPassword().equals("pass")) {
                    currentUser.incrementFailed();

                    // Username or password false, display and an error
                    AlertDialog.Builder alert  = new AlertDialog.Builder(Login.this);

                    alert.setMessage("Wrong Username and/or Password");
                    alert.setTitle("Oops!");
                    alert.setPositiveButton("OK", null);
                    alert.setCancelable(true);
                    alert.create().show();

                    alert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                }

                if (currentUser.getFailedAttempts() >= 3) {
                    // Lock Account
                }


            }
        });


    }

}
