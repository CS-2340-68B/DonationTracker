package edu.gatech.cs2340_68b.donationtracker.View;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;


public class SearchResult extends AppCompatActivity {
    private int searchTypeFlag;
    private Location locationSearch;
    private String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchTypeFlag = (int) getIntent().getSerializableExtra("SearchFlag");
        // This should be null if it does not need location
        locationSearch =(Location) getIntent().getSerializableExtra("LOCATIONSEARCH");
        searchString = (String) getIntent().getSerializableExtra("SearchString");

        

        setContentView(R.layout.search_result);


        // Show list

//        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("cat?loc?");
//        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {}

    }



}
