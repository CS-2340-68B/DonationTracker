package edu.gatech.cs2340_68b.donationtracker.View;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;


public class SearchResult extends AppCompatActivity {
    private int searchTypeFlag;
    private Location locationSearch;
    private String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        searchTypeFlag = (int) getIntent().getSerializableExtra("SearchFlag");
        // This should be null if it does not need location
        locationSearch =(Location) getIntent().getSerializableExtra("LOCATIONSEARCH");
        searchString = (String) getIntent().getSerializableExtra("SearchString");
        Log.e("What is this? ", searchTypeFlag + " " + searchString + " " + locationSearch);

        // Show list

//        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("cat?loc?");
//        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {}
        DatabaseReference donationDB = FirebaseDatabase.getInstance().getReference("donations");
        if (searchTypeFlag == 0) {
            Query query = donationDB.child("AFD Station 4").orderByChild("name").equalTo(searchString);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        // Create local copy of one location
                        DonationDetail donation = snapshot.getValue(DonationDetail.class);
                        Log.e("Item: ", donation.getName());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (searchTypeFlag == 1) {

        }
    }
}
