package edu.gatech.cs2340_68b.donationtracker.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import edu.gatech.cs2340_68b.donationtracker.R;

public class DonationList_Own extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_list_own);
    }
}
