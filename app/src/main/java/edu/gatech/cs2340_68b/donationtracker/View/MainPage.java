package edu.gatech.cs2340_68b.donationtracker.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationListView;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationListViewPriv;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchView;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.currentUser;

public class MainPage extends AppCompatActivity {

    private Button logout;
    private ActionBar actionBar;
    private Button locationList;
    private Button userProfile;
    private Button search;

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
                if (currentUser.getType().toString().equals("MANAGER") || currentUser.getType().toString().equals("LOCATIONEMPLOYEE")) {
                    currentUser.setAssignedLocation("AFD Station 4");
                    Intent intent = new Intent(MainPage.this, LocationListViewPriv.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainPage.this, LocationListView.class);
                    startActivity(intent);
                }

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

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, SearchView.class);
                startActivity(intent);
            }
        });
    }
}

