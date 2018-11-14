package edu.gatech.cs2340_68b.donationtracker.Controllers.Location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.Login;
import edu.gatech.cs2340_68b.donationtracker.View.UserProfile;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationListView;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchHistory;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchView;

/**
 * A controller that connect to the location set view and gather and populate data from database
 */
public class LocationSet extends AppCompatActivity {

    private TextInputEditText locationName;
    private TextInputEditText cityName;
    private TextInputEditText addressName;
    private TextInputEditText phoneNumber;
    private TextInputEditText websiteName;
    private TextInputEditText zipcode;
    private Spinner stateChoose;
    private Spinner locationType;
    private ActionBarDrawerToggle aToggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_set);

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

        // Initialize variables
        Button submitButton = findViewById(R.id.submitButton);
        stateChoose = findViewById(R.id.stateChoose);
        locationType = findViewById(R.id.locationType);
        locationName = findViewById(R.id.locationInput);
        TextInputLayout locationLayout = findViewById(R.id.locationBox);
        cityName = findViewById(R.id.cityInput);
        TextInputLayout cityLayout = findViewById(R.id.cityBox);
        addressName = findViewById(R.id.addressInput);
        TextInputLayout addressLayout = findViewById(R.id.addressBox);
        phoneNumber = findViewById(R.id.phoneInput);
        TextInputLayout phoneLayout = findViewById(R.id.phoneBox);
        websiteName = findViewById(R.id.websiteInput);
        TextInputLayout webLayout = findViewById(R.id.websiteBox);
        zipcode = findViewById(R.id.zipcodeInput);
        TextInputLayout zipLayout = findViewById(R.id.zipcodeBox);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Convert to string
                String addressNameStr = Objects.requireNonNull(addressName.getText()).toString();
                String locationNameStr = Objects.requireNonNull(locationName.getText()).toString();
                String cityNameStr = Objects.requireNonNull(cityName.getText()).toString();
                String zipcodeStr = Objects.requireNonNull(zipcode.getText()).toString();
                String stateChooseStr = stateChoose.getSelectedItem().toString();
                String phoneNumberStr = Objects.requireNonNull(phoneNumber.getText()).toString();
                String websiteNameStr = Objects.requireNonNull(websiteName.getText()).toString();
                String locationTypeStr = locationType.getSelectedItem().toString();

                // Get the full address if store
                String fullAddress = "" + addressNameStr + ", "
                        + cityNameStr + ", " + zipcodeStr + " "
                        + stateChooseStr;

                // Generate helper method to get longitude
                List<Integer> data = getLongitudeAttitude(fullAddress);

                // Get out the next key for new location
                String newKey = getKey();

                // Update the new location to DB
                Location location =
                        new Location(newKey, locationNameStr,
                                null, null,
                                addressNameStr, cityNameStr,
                                stateChooseStr, zipcodeStr,
                                locationTypeStr, phoneNumberStr,
                                websiteNameStr);
                addNewLocationToDB(location);
            }
        });
    }
    private void addNewLocationToDB(Location location) {
        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebase.getReference("locations").push();
        ref.setValue(location);
    }

    // Generate the longitude and attitude base on the email address
    private List<Integer> getLongitudeAttitude(String locationAddress) {
        List<Integer> output = new ArrayList<>(2);

        return output;
    }

    // Get out the next key should do
    private String getKey() {
        String output = "0";

        // Loop through every single key find the max + 1

        // output += 1
        return output;
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
