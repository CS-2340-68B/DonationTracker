package edu.gatech.cs2340_68b.donationtracker.Controllers.Login;

import android.content.Intent;
import android.app.AlertDialog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat;
import edu.gatech.cs2340_68b.donationtracker.Controllers.MainPage;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.R;


public class Login extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private Button login;
    private Button cancel;
    public User currentUser;
    private int loginClick = 0;
    private Map<String, Integer> typedUsername = new HashMap<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("accounts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (TextView)findViewById(R.id.registerUsername);
        password = (TextView)findViewById(R.id.registerPassword);
        cancel = (Button)findViewById(R.id.cancel);
        login = (Button)findViewById(R.id.login);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputUsername = username.getText().toString();
                String inputPassword = username.getText().toString();

                if (!VerifyFormat.verifyEmailFormat(inputUsername)) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Login.this,
                            "Format Error", "Email format is not correct");
                    alert.create().show();
                    return;
                }

                currentUser = new User(inputUsername, inputPassword);

                // When verified, move to main page
                if (currentUser.getUsername().equals("user") &&
                        currentUser.getPassword().equals("pass")) {
                    Intent intent = new Intent(Login.this, MainPage.class);
                    startActivity(intent);
                }

                //Basic implementation of account lock out
                else {
                    // Username or password false, display and an error
                    AlertDialog.Builder alert  = CustomDialog.errorDialog(Login.this,
                            "Oops", "Wrong Username and/or Password");
                    alert.create().show();
                    AccountModify.lockAccount(inputUsername);
                }
            }
        });
    }
}
