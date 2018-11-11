package edu.gatech.cs2340_68b.donationtracker.View;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationListView;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchView;

/**
 * Controller for the main page of the app
 */
public class MainPage extends AppCompatActivity {
    private ActionBarDrawerToggle aToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.main_page);

        Toolbar aToolbar = findViewById(R.id.nav_actionbar);
        setSupportActionBar(aToolbar);
        DrawerLayout nDrawerLayout = findViewById(R.id.drawerLayout);
        aToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open, R.string.close);
        nDrawerLayout.addDrawerListener(aToggle);
        aToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



        Button logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button locationList = findViewById(R.id.donationLocationListButton);
//        (findViewById(R.id.menu)).setVisibility(View.INVISIBLE);
        locationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, LocationListView.class);
                startActivity(intent);

            }
        });

        Button userProfile = findViewById(R.id.profileButton);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, UserProfile.class);
                startActivity(intent);
            }
        });
//        actionBar = getSupportActionBar();

        Button search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, SearchView.class);
                startActivity(intent);
            }
        });

        Button menuT = findViewById(R.id.menu);
        menuT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Menu.class);
                startActivity(intent);
            }
        });







    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (aToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
}

