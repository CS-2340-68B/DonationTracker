package edu.gatech.cs2340_68b.donationtracker.View.locationViews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import java.util.Objects;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.DataListAdapter;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;

public class LocationListView extends AppCompatActivity {

    private ListView locationListView;
    private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list_view);

        locationListView = findViewById(R.id.locationList);
        mapButton = findViewById(R.id.MapButton);

        // Get locations from firebase
        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("locations");

        // Get values from locations
        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Map.Entry<String, String>> locationInfo = new ArrayList<>();
                final ArrayList<Location> locationList = new ArrayList<>();
                // Loads in all locations into the array list
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    // Create local copy of one location
                    Location place = snapshot.getValue(Location.class);
                    locationList.add(place);
                    Map.Entry<String,String> entry =
                            new AbstractMap.SimpleEntry<>(Objects.requireNonNull(place).getLocationName(), place.getAddress());
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
                locationListView.setAdapter(new DataListAdapter(locationInfo, getLayoutInflater()));
                locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Sending information through intent
                        Location l = locationList.get(position);
                        Intent detail = new Intent(LocationListView.this, LocationDetail.class);
                        detail.putExtra("LOCATION", l);
                        startActivity(detail);
                    }
                });

                mapButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent locationMapView = new Intent(LocationListView.this, LocationMap.class);
                        locationMapView.putExtra("LocationList", locationList);
                        startActivity(locationMapView);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


