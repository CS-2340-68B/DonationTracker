package edu.gatech.cs2340_68b.donationtracker.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Location.LocationControl;

/**
 * The welcome page controller for the app
 */

public class Welcome extends AppCompatActivity {

    public static User currentUser = new User();
    public static String userKey;
    public static final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        Button loginButton = findViewById(R.id.LoginButton);
        Button registerButton = findViewById(R.id.RegisterButton);

        Button testButton = findViewById(R.id.TestButton);
        testButton.setVisibility(View.VISIBLE);

        // Adding location to database
        LocationControl a = new LocationControl(this);
        a.readCSVFile();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Login.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
