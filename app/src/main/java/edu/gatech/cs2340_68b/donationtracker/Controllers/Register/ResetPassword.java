package edu.gatech.cs2340_68b.donationtracker.Controllers.Register;

import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.R;

@SuppressWarnings({"AssignmentToStaticFieldFromInstanceMethod", "PublicField"})
public class ResetPassword extends AppCompatActivity {

    private EditText newPassword;
    private EditText repeatNewPassword;
    private TextInputLayout til;
    private String currentUserEmail;
    public static boolean finishedFlag = false;
    private static final int NUM_1500 = 1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        newPassword = findViewById(R.id.PasswordField);
        repeatNewPassword = findViewById(R.id.repeatPasswordField);
        til = findViewById(R.id.repeatPasswordOut);
        Button resetPasswordButton = findViewById(R.id.resetButton);

        // Getting current user email from reset page
        Intent intent = getIntent();
        currentUserEmail = intent.getStringExtra("userEmail");

        // Reset password button click event
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPasswordFromUser = newPassword.getText().toString().trim();
                String newRepeatPasswordFromUser = repeatNewPassword.getText().toString().trim();

                if (!newRepeatPasswordFromUser.equals(newPasswordFromUser)) {
                    til.setError("You password does not match.");
                } else if(newPasswordFromUser.length() < 8) {
                    til.setError("You password length can not less than 8 characters.");
                } else if (!VerifyFormat.verifyPassword(newPasswordFromUser)) {
                    til.setError("Your password must contain at least 1 letter, 1 number, and " +
                            "one upper case letter");
                } else {
                    updatePasswordToDB(newPasswordFromUser);
                }
            }

            private void updatePasswordToDB(final String newPassword) {
                final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                final DatabaseReference ref = firebase.getReference("accounts");
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                System.out.println("Line 63: " + currentUserEmail);
                Query query = reference.child(
                        "accounts").orderByChild(
                                "username").equalTo(
                                        currentUserEmail);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot singleSnapShot: dataSnapshot.getChildren()) {
                            User user = singleSnapShot.getValue(User.class);
                            Objects.requireNonNull(user)
                                    .setPassword(PasswordEncryption.encode(newPassword));
                            user.setFailedAttempts(0);
                            System.out.println("Line 71: " + user.getPassword());
                            finishedFlag = true;
                            ref.child(Objects
                                    .requireNonNull(singleSnapShot
                                            .getKey())).setValue(user);
                            AlertDialog.Builder alert  = CustomDialog.errorDialog(
                                    ResetPassword.this,
                                    "Congratulation", "You have successfully changed your password."
                                + " We will prompt you back to login page.");
                            alert.create().show();
                            new android.os.Handler().postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                },
                            NUM_1500);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Need to throw exception
                    }
                });
            }
        });
    }
}
