package edu.gatech.cs2340_68b.donationtracker.Controllers.Login;

import android.content.Intent;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslCertificate;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat;
import edu.gatech.cs2340_68b.donationtracker.Controllers.MainPage;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Welcome;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Register.ForgetPassword;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.R;

import static edu.gatech.cs2340_68b.donationtracker.Controllers.Welcome.currentUser;
import static edu.gatech.cs2340_68b.donationtracker.Controllers.Welcome.tempDB;


public class Login extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private Button login;
    private Button cancel;
    private int loginClick = 0;
    private TextView resetPassword;
    private Map<String, Integer> typedUsername = new HashMap<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("accounts");
    private ChildEventListener mChildListener;
    private ActionBar actionBar;

    private boolean validAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        username = (TextView)findViewById(R.id.registerUsername);
        password = (TextView)findViewById(R.id.registerPassword);
        cancel = (Button)findViewById(R.id.cancel);
        login = (Button)findViewById(R.id.login);
        resetPassword = (TextView)findViewById(R.id.forgetPassword);

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
                String inputPassword = password.getText().toString();
                //inputPassword = PasswordEncryption.encode(inputPassword);

                if (!VerifyFormat.verifyEmailFormat(inputUsername)) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Login.this,
                            "Format Error", "Email format is not correct");
                    alert.create().show();
                    return;
                }

                // When verified, move to main page
//                if (inputUsername.equals(tempDB.getTempUser().getUsername()) &&
//                        inputPassword.equals(tempDB.getTempUser().getPassword())) {
//                    Welcome.currentUser = tempDB.getTempUser();
//                    Intent intent = new Intent(Login.this, MainPage.class);
//                    startActivity(intent);
//                }
//
//                //Basic implementation of account lock out
//                else {
//                    currentUser.setFailedAttempts(currentUser.getFailedAttempts()+1);
//                    if (currentUser.getFailedAttempts() >= 3) {
//                        AccountModify.lockAccount(currentUser.getUsername());
//                    }
//                    // Username or password false, display and an error
//                    AlertDialog.Builder alert  = CustomDialog.errorDialog(Login.this,
//                            "Oops", "Wrong Username and/or Password");
//                    alert.create().show();
//                }

                currentUser = new User(inputUsername, inputPassword);
                gatewayLogin(inputUsername, inputPassword);
            }

            private void gatewayLogin(final String userName, final String password) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("accounts").orderByChild("username").equalTo(userName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            AlertDialog.Builder alert  = CustomDialog.errorDialog(Login.this,
                                    "Oops", "Email does not exist.");
                            alert.create().show();
                            return;
                        }
                        for (DataSnapshot singleSnapShot: dataSnapshot.getChildren()) {
                            User user = singleSnapShot.getValue(User.class);
                            if (user.getIsLock()) {
                                AlertDialog.Builder alert  = CustomDialog.errorDialog(Login.this,
                                        "Sorry", "Account is currently lock. " +
                                                "Please reset your password or check your email");
                                alert.create().show();
                            } else if (user.getUsername().equals(userName.trim()) && user.getPassword().equals(password.trim())) {
                                AccountModify.resetAttemptCount(userName);
                                Intent intent = new Intent(Login.this, MainPage.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder alert  = CustomDialog.errorDialog(Login.this,
                                        "Oops", "Wrong Username and/or Password");
                                alert.create().show();
                                AccountModify.lockAccount(userName);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        AlertDialog.Builder alert  = CustomDialog.errorDialog(Login.this,
                                "Critical Error", "Database Error. Please try again later.");
                        alert.create().show();
                    }
                });
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
    }
}
