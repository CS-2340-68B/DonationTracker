package edu.gatech.cs2340_68b.donationtracker.Controllers.Location;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;

public class LocationDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail);
        Location location = (Location) getIntent().getSerializableExtra("LOCATION");
        Log.e("This is cool", location.toString());
    }
}
