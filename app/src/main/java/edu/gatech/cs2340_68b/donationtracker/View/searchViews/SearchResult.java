package edu.gatech.cs2340_68b.donationtracker.View.searchViews;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.DataListAdapter;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.donationViews.DonationDetailControl;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationDetail;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationListView;


public class SearchResult extends AppCompatActivity {
    private int searchTypeFlag;
    private Location locationSearch;
    private String searchString;
    private ListView searchresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        searchresult = findViewById(R.id.searchresult);
        searchTypeFlag = (int) getIntent().getSerializableExtra("SearchFlag");
        // This should be null if it does not need location
        locationSearch =(Location) getIntent().getSerializableExtra("LOCATIONSEARCH");
        searchString = (String) getIntent().getSerializableExtra("SearchString");


        DatabaseReference donationDB = FirebaseDatabase.getInstance().getReference("donations");
        Query query = null;
        if (searchTypeFlag == 0) {
            query = donationDB.orderByChild("name").equalTo(searchString);
        } else if (searchTypeFlag == 1) {
            query = donationDB.orderByChild("category").equalTo(searchString);
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Map.Entry<String, String>> donationInfo = new ArrayList<>();
                final ArrayList<DonationDetail> donationList = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    DonationDetail detail = snapshot.getValue(DonationDetail.class);
                    donationList.add(detail);
                    Map.Entry<String,String> entry =
                            new AbstractMap.SimpleEntry<>(detail.getName(), detail.getFullDescription());
                    donationInfo.add(entry);
                }
                searchresult.setAdapter(new DataListAdapter(donationInfo, getLayoutInflater()));
                searchresult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Sending information through intent
                        DonationDetail l = donationList.get(position);
                        Intent detail = new Intent(SearchResult.this, DonationDetailControl.class);
                        startActivity(detail);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
