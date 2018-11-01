package edu.gatech.cs2340_68b.donationtracker.View;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.Models.Enum.UserType;
import edu.gatech.cs2340_68b.donationtracker.R;

public class Register extends AppCompatActivity {

    private TextView usernameTV;
    private TextView passwordTV;
    private TextView confirmPasswordTV;
    private Button register;
    private Button cancel;
    private ActionBar actionBar;
    private Spinner utspinner;
    private Spinner locspinner;
    private User newAccount = new User();

    final int ADMIN = 0;
    final int USER = 1;
    final int LOCATIONEMPLOYEE = 2;
    final int MANAGER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout .register);
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
        locspinner = (Spinner) findViewById(R.id.locationSpinner);

        // Read in location
        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("locations");
        final ArrayList<Location> locationList = new ArrayList<>();
        final ArrayList<String> locationListString = new ArrayList<>();
        // Get values from locations
        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Map.Entry<String, String>> locationInfo = new ArrayList<>();
                // Loads in all locations into the array list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Create local copy of one location
                    Location place = snapshot.getValue(Location.class);
                    locationList.add(place);
                    locationListString.add(place.getLocationName());
                    Map.Entry<String, String> entry =
                            new AbstractMap.SimpleEntry<>(place.getLocationName(), place.getAddress());
                    locationInfo.add(entry);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*
          Set up the adapter to display the user types in the spinner
         */
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UserType.values());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        utspinner.setAdapter(adapter1);

        /*
          Set up the adapter to display the locations in the spinner
         */
        ArrayAdapter<String> adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, locationListString);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locspinner.setAdapter(adapter2);

        // UT Listener
        utspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case USER:
                        newAccount.setType(UserType.USER);
                        newAccount.setAssignedLocation(null);
                        locspinner.setVisibility(View.GONE);
                        break;
                    case LOCATIONEMPLOYEE:
                        newAccount.setType(UserType.LOCATIONEMPLOYEE);
                        locspinner.setVisibility(View.VISIBLE);
                        //newAccount.setAssignedLocation(locspinner.getSelectedItem().toString());
                        break;
                    case ADMIN:
                        newAccount.setType(UserType.ADMIN);
                        newAccount.setAssignedLocation(null);
                        locspinner.setVisibility(View.GONE);
                        break;
                    case MANAGER:
                        newAccount.setType(UserType.MANAGER);
                        locspinner.setVisibility(View.VISIBLE);
                        //newAccount.setAssignedLocation(locspinner.getSelectedItem().toString());
                        break;
                    default:
                        newAccount.setType(UserType.USER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        // Location Listener
        locspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("MYTAG", "Stringhere"); //locationListString.get(position)
                //Toast.makeText(parentView.getContext(),locationListString.get(position), Toast.LENGTH_LONG).show();
                newAccount.setAssignedLocation(locationList.get(position).getLocationName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d("MYTAG", "Nothing Selected"); //locationListString.get(position)
            }

        });

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

                else if (!password.equals(confirmPassword)) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Error", "Password are not the same.");
                    alert.create().show();
                    return;
                }

                else if (!VerifyFormat.verifyPassword(password)) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Error", "Your password must contain at least 1 letter, 1 number, and " +
                            "one upper case letter, and be at least 8 characters long");
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
                                newAccount.setUsername(username);
                                newAccount.setPassword(PasswordEncryption.encode(password));
                                newAccount.setType(type);
//                                if (locspinner.getSelectedItem() == null) {
//                                    Log.d("MYTAG", "Null");
//                                } else {
//                                    Log.d("MYTAG", "AssLoc: " + locspinner.getSelectedItem().toString());
//                                }
                                if (newAccount.getType().equals(UserType.LOCATIONEMPLOYEE)
                                        || newAccount.getType().equals(UserType.MANAGER)) {
                                    newAccount.setAssignedLocation(locspinner.getSelectedItem().toString());
                                }
                                // Creates a new empty key
                                DatabaseReference newRef = ref.push();
                                // Set value
                                newRef.setValue(newAccount);
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);

                                //Update database

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
