package edu.gatech.cs2340_68b.donationtracker.Controllers.Register;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Login.Login;
import edu.gatech.cs2340_68b.donationtracker.Controllers.MainPage;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Welcome;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.Models.UserType;
import edu.gatech.cs2340_68b.donationtracker.R;

public class Register extends AppCompatActivity {

    private TextView usernameTV;
    private TextView passwordTV;
    private TextView confirmPasswordTV;
    private Button register;
    private Button cancel;
    private ActionBar actionBar;
    private Spinner utspinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        final DatabaseReference ref = firebase.getReference("accounts");

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));

        usernameTV = (TextView)findViewById(R.id.registerUsername);
        passwordTV = (TextView)findViewById(R.id.registerPassword);
        confirmPasswordTV = (TextView)findViewById(R.id.registerConfirmPassword);
        register = (Button)findViewById(R.id.register);
        cancel = (Button)findViewById(R.id.cancel);
        utspinner = (Spinner)findViewById(R.id.userTypeSpinner);

        /*
          Set up the adapter to display the allowable majors in the spinner
         */
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UserType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        utspinner.setAdapter(adapter);

        //standingSpinner.setSelection(Student.findPosition(_student.getClassStanding()));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameTV.getText().toString();
                final String password = passwordTV.getText().toString();
                final UserType type = (UserType) utspinner.getSelectedItem();
                String confirmPassword = confirmPasswordTV.getText().toString();

                if (!VerifyFormat.verifyEmailFormat(username)) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Error", "User account format is not correct.");
                    alert.create().show();
                    return;
                }
                if (username.equals(Welcome.tempDB.getTempUser().getUsername())) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Registration Error", "Username already exists");
                    alert.create().show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Error", "Password are not the same.");
                    alert.create().show();
                    return;
                }
                else {
                    Query accountQuery = ref.orderByChild("username").equalTo(username);
                    accountQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                                        "Registration Error", "Username already exists");
                                alert.create().show();
                            }
                            else {
                                User newAccount = new User(username, PasswordEncryption.encode(password));
                                DatabaseReference newRef = ref.push();
                                newRef.setValue(newAccount);
                                Intent intent = new Intent(Register.this, MainPage.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
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
