package edu.gatech.cs2340_68b.donationtracker.Controllers;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Location.LocationPage;
import edu.gatech.cs2340_68b.donationtracker.R;

public class MainPage extends AppCompatActivity {

    private Button logout;
    private ActionBar actionBar;
    private Button locationList;
    private Button userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        setContentView(R.layout.main_page);
        logout = (Button) findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        locationList = (Button) findViewById(R.id.donationLocationListButton);
        locationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, LocationPage.class);
                startActivity(intent);
            }
        });

        userProfile = (Button) findViewById(R.id.profileButton);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, UserProfile.class);
                startActivity(intent);
            }
        });
        actionBar = getSupportActionBar();




//        actionBar.setTitle("");
//        actionBar.setIcon(R.drawable.icon);
//        actionBar.setDisplayUseLogoEnabled(true);
    }

}

