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

    private String LocationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail);
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
        TextView longitude = findViewById(R.id.longtitude);
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
}
