package edu.gatech.cs2340_68b.donationtracker.View.locationViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.donationViews.DonationList;

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
    private String LocationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail);
        final Location location = (Location) getIntent().getSerializableExtra("LOCATION");
        LocationName = location.getLocationName();
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
                Intent intent = new Intent(LocationDetail.this, DonationList.class);
                intent.putExtra("PLACENAME", LocationName);
                startActivity(intent);
            }
        });
    }
}
