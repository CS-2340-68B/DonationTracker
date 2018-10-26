package edu.gatech.cs2340_68b.donationtracker.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.cs2340_68b.donationtracker.View.Register;
import edu.gatech.cs2340_68b.donationtracker.View.Welcome;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;

public class LocationDetail extends AppCompatActivity {

    private TextView name;
    private TextView streetAddress;
    private TextView city;
    private TextView state;
    private TextView zip;
    private TextView type;
    private TextView phone;
    private TextView website;
    private TextView longtitude;
    private TextView latitude;
    private Button DonationListBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail);
        Location location = (Location) getIntent().getSerializableExtra("LOCATION");
        name = findViewById(R.id.name);
        streetAddress = findViewById(R.id.streetAddress);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        zip = findViewById(R.id.zip);
        type = findViewById(R.id.type);
        phone = findViewById(R.id.phone);
        website = findViewById(R.id.website);
        latitude = findViewById(R.id.latitude);
        longtitude = findViewById(R.id.longtitude);
        DonationListBt = findViewById(R.id.donationListBt);


        name.setText(location.getLocationName());
        streetAddress.setText(location.getStreetAddress());
        city.setText(location.getCity());
        state.setText(location.getState());
        zip.setText(location.getZip());
        type.setText(location.getLocationType());
        phone.setText(location.getPhone());
        website.setText(location.getWebsite());
        latitude.setText(location.getLatitude());
        longtitude.setText(location.getLongitude());

        DonationListBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Welcome.currentUser.getType().equals("ADMIN") || Welcome.currentUser.getType().equals("USER")) {
                    Intent intent = new Intent(LocationDetail.this, DonationList.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LocationDetail.this, DonationList_Own.class);
                    startActivity(intent);
                }
            }
        });
    }
}
