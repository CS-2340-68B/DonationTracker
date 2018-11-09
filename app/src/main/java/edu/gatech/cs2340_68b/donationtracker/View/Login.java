package edu.gatech.cs2340_68b.donationtracker.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Login.AccountModify;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Register.ForgetPassword;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.R;

/**
 * Controller for login to the app, check account in the database
 */
public class Login extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private Map<String, Integer> typedUsername = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = findViewById(R.id.registerUsername);
        password = findViewById(R.id.registerPassword);
        Button cancel = findViewById(R.id.cancel);
        Button login = findViewById(R.id.login);
        TextView resetPassword = findViewById(R.id.forgetPassword);

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
                inputPassword = PasswordEncryption.encode(inputPassword);
                gatewayLogin(inputUsername, inputPassword);

//                RequestParams query =  new RequestParams();
//                query.put("email_signin", inputUsername);
//                query.put("password_signin", inputPassword);
//                HttpUtils.postForm("/signin", query, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        super.onSuccess(statusCode, headers, response);
//                        Response res = gson.fromJson(response.toString(), Response.class);
//                        Log.e("Tag: ", res.status);
//                        if (res.status.equals("noAccount")) {
//                            AlertDialog.Builder alert  = CustomDialog.errorDialog(Login.this,
//                                    "Oops", "Email does not exist.");
//                            alert.create().show();
//                            return;
//                        } else if (res.status.equals("wrongPassword")) {
//                            AlertDialog.Builder alert  = CustomDialog.errorDialog(Login.this,
//                                    "Oops", "Wrong password.");
//                            alert.create().show();
//                        } else if (res.status.equals("accountLock")) {
//                            AlertDialog.Builder alert  = CustomDialog.errorDialog(Login.this,
//                                    "Sorry", "Account is currently lock. " +
//                                            "Please reset your password or check your email");
//                            alert.create().show();
//                        } else {
//                            User user = gson.fromJson(
//                                    gson.toJsonTree(res.data).getAsJsonObject(), User.class);
//                            Welcome.currentUser = user;
//                            Welcome.userKey = user.getUserKey();
//                            Intent intent = new Intent(Login.this, MainPage.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                });
            }

            private void gatewayLogin(final String userName, final String password) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
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

                            if (Objects.requireNonNull(user).getIsLock()) {
                                AlertDialog.Builder alert  = CustomDialog.errorDialog(Login.this,
                                        "Sorry", "Account is currently lock. " +
                                                "Please reset your password or check your email");
                                alert.create().show();
                            } else if (user.getUsername().equals(userName.trim()) && user.getPassword().equals(password.trim())) {
                                AccountModify.resetAttemptCount(userName);
                                Welcome.currentUser = user;
                                Welcome.userKey = singleSnapShot.getKey();
                                Intent intent = new Intent(Login.this, MainPage.class);
                                startActivity(intent);
                                finish();
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
