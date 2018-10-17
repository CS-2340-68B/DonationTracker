package edu.gatech.cs2340_68b.donationtracker.Controllers.Location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.location.Geocoder;
import android.location.Address;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;

public class LocationBuild extends AppCompatActivity {

    private TextInputEditText locationName;
    private TextInputEditText cityName;
    private TextInputEditText addressName;
    private TextInputEditText phoneNumber;
    private TextInputEditText websiteName;
    private TextInputEditText zipcode;
    private TextInputLayout locationLayout;
    private TextInputLayout cityLayout;
    private TextInputLayout addressLayout;
    private TextInputLayout phoneLayout;
    private TextInputLayout zipLayout;
    private TextInputLayout webLayout;
    private Button submitButton;
    private Spinner stateChoose;
    private Spinner locationType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_set);

        // Initialize variables
        submitButton = findViewById(R.id.submitButton);
        stateChoose = findViewById(R.id.stateChoose);
        locationType = findViewById(R.id.locationType);
        locationName = findViewById(R.id.locationInput);
        locationLayout = findViewById(R.id.locationBox);
        cityName = findViewById(R.id.cityInput);
        cityLayout = findViewById(R.id.cityBox);
        addressName = findViewById(R.id.addressInput);
        addressLayout = findViewById(R.id.addressBox);
        phoneNumber = findViewById(R.id.phoneInput);
        phoneLayout = findViewById(R.id.phoneBox);
        websiteName = findViewById(R.id.websiteInput);
        webLayout = findViewById(R.id.websiteBox);
        zipcode = findViewById(R.id.zipcodeInput);
        zipLayout = findViewById(R.id.zipcodeBox);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Convert to string
                String addressNameStr = addressName.getText().toString();
                String locationNameStr = locationName.getText().toString();
                String cityNameStr = cityName.getText().toString();
                String zipcodeStr = zipcode.getText().toString();
                String stateChooseStr = stateChoose.getSelectedItem().toString();
                String phoneNumnberStr = phoneNumber.getText().toString();
                String websiteNameStr = websiteName.getText().toString();
                String locationTypeStr = locationType.getSelectedItem().toString();

                // Get the full address if store
                String fullAddress = "" + addressNameStr + ", " + cityNameStr + ", " + zipcodeStr + " " + stateChooseStr;

                // Generate helper method to get longitude
                List<Integer> data = getLongitudeAttitude(fullAddress);
//                String longitude = Integer.toString(data[0]);
                String longitude = null;
//                String latitude = Integer.toString(data[1]);
                String latitude = null;

                // Get out the next key for new location
                String newKey = getKey();

                // Update the new location to DB
                Location location = new Location(newKey, locationNameStr, latitude, longitude, addressNameStr, cityNameStr, stateChooseStr, zipcodeStr, locationTypeStr, phoneNumnberStr, websiteNameStr);
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
}
