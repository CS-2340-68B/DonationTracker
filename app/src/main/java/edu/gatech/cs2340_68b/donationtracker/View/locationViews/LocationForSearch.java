package edu.gatech.cs2340_68b.donationtracker.View.locationViews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.DataListAdapter;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchResult;

public class LocationForSearch extends AppCompatActivity {

    private ListView locationListViewForSearch;
    private Location locationSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_for_search);
        locationListViewForSearch = findViewById(R.id.locationListForSearch);
        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("locations");
        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Map.Entry<String, String>> locationInfo = new ArrayList<>();
                final ArrayList<Location> locationList = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Location place = snapshot.getValue(Location.class);
                    locationList.add(place);
                    Map.Entry<String,String> entry =
                            new AbstractMap.SimpleEntry<>(place.getLocationName(), place.getAddress());
                    locationInfo.add(entry);
                }
                Collections.sort(locationInfo, new Comparator<Map.Entry<String, String>>() {
                    @Override
                    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                        return o1.getKey().compareTo(o2.getKey());
                    }
                });
                Collections.sort(locationList, new Comparator<Location>() {
                    @Override
                    public int compare(Location o1, Location o2) {
                        return o1.getLocationName().compareTo(o2.getLocationName());
                    }
                });
                locationListViewForSearch.setAdapter(new DataListAdapter(locationInfo, getLayoutInflater()));
                locationListViewForSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Sending information through intent
                        locationSearch = locationList.get(position);
                        int searchTypeFlag = (int) getIntent().getSerializableExtra("SearchFlag");
                        Intent intent = new Intent(LocationForSearch.this, SearchResult.class);
                        intent.putExtra("SearchFlag", searchTypeFlag);
                        intent.putExtra("LOCATIONSEARCH", locationSearch);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


