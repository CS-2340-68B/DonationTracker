package edu.gatech.cs2340_68b.donationtracker.Controllers.Register;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat;


import edu.gatech.cs2340_68b.donationtracker.R;


public class ForgetPassword extends AppCompatActivity {
    private TextInputEditText userEmail;
    private TextInputLayout til;
    private ProgressBar progressBar;
    @SuppressWarnings("FieldMayBeFinal")
    private Handler progressHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        Button resetButton = (Button) findViewById(R.id.resetButton);
        Button goBack = (Button) findViewById(R.id.goBack);
        userEmail = findViewById(R.id.emailUser);
        til = (TextInputLayout) findViewById(R.id.emailInput);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        // Reset button click
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                String inputEmail = userEmail.getText().toString().trim();
                if ((inputEmail.length() != 0) && VerifyFormat.verifyEmailFormat(inputEmail)) {
                    checkIfExistInDB(inputEmail);
                } else {
                    til.setError("Email format is not correct.");
                }
            }

            // Helper method to check if email is in DB
            private void checkIfExistInDB(final String inputEmail) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("accounts").orderByChild("username").equalTo(inputEmail);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean status = true;
                        while (status) {
                            progressHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(1);
                                }
                            });
                            if (!dataSnapshot.exists()) {
                                til.setError("Email does not exist in out database");
                                status = false;
                            } else {
                                // Move to reset page
                                status = false;
                                Intent intent = new Intent(ForgetPassword.this, ResetPassword.class);
                                intent.putExtra("userEmail", inputEmail);
                                startActivity(intent);
                                if (ResetPassword.finishedFlag == true) {
                                    ResetPassword.finishedFlag = false;
                                    finish();
                                }
                            }
                        }
                        if (!status) {
                            progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Need to throw exception
                    }
                });
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