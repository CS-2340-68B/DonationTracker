package edu.gatech.cs2340_68b.donationtracker.Controllers.Location;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import edu.gatech.cs2340_68b.donationtracker.R;

public class DonationDetail extends AppCompatActivity {
    private ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        setContentView(R.layout.donation_detail);
        actionBar = getSupportActionBar();

    }


}
