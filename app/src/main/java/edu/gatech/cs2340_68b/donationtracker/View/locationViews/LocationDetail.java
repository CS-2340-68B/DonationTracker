package edu.gatech.cs2340_68b.donationtracker.View.locationViews;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.Login;
import edu.gatech.cs2340_68b.donationtracker.View.UserProfile;
import edu.gatech.cs2340_68b.donationtracker.View.donationViews.DonationList;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchHistory;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchView;

/**
 * This class control location detail
 * @version 1.0
 */
public class LocationDetail extends AppCompatActivity {

    private String LocationName;
    private ActionBarDrawerToggle aToggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail);
        final Context contect = this;

        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Toolbar aToolbar = findViewById(R.id.nav_actionbar);
        setSupportActionBar(aToolbar);
        aToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(aToggle);
        aToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        if (menuItem.getItemId() == R.id.nav_account) {
                            Intent intent = new Intent(contect ,UserProfile.class);
                            startActivity(intent);
                        }
                        if (menuItem.getItemId() == R.id.nav_search) {
                            Intent intent = new Intent(contect ,SearchView.class);
                            startActivity(intent);
                        }
                        if (menuItem.getItemId() == R.id.nav_location) {
                            Intent intent = new Intent(contect ,LocationListView.class);
                            startActivity(intent);
                        }
                        if (menuItem.getItemId() == R.id.nav_history) {
                            Intent intent = new Intent(contect ,SearchHistory.class);
                            startActivity(intent);
                        }
                        if (menuItem.getItemId() == R.id.nav_logout) {
                            Intent intent = new Intent(contect, Login.class);
                            startActivity(intent);
                        }
                        // close drawer when item is tapped
                        drawer.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return true;
                    }
                });



        final Location location = (Location) getIntent().getSerializableExtra("LOCATION");
        LocationName = location.getLocationName();
        TextView name = findViewById(R.id.name);
        TextView streetAddress = findViewById(R.id.streetAddress);
        TextView city = findViewById(R.id.city);
        TextView state = findViewById(R.id.state);
        TextView zip = findViewById(R.id.zip);
        TextView type = findViewById(R.id.type);
        TextView phone = findViewById(R.id.phone);
        TextView website = findViewById(R.id.website);
        TextView latitude = findViewById(R.id.latitude);
        TextView longitude = findViewById(R.id.longitude);
        Button donationListBt = findViewById(R.id.donationListBt);

        name.setText(location.getLocationName());
        streetAddress.setText(location.getStreetAddress());
        city.setText(location.getCity());
        state.setText(location.getState());
        zip.setText(location.getZip());
        type.setText(location.getLocationType());
        phone.setText(location.getPhone());
        website.setText(location.getWebsite());
        latitude.setText(location.getLatitude());
        longitude.setText(location.getLongitude());

        donationListBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationDetail.this, DonationList.class);
                intent.putExtra("PLACENAME", LocationName);
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
