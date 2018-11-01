package edu.gatech.cs2340_68b.donationtracker.View.locationViews;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.DataListAdapter;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.donationViews.DonationList_Own;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.currentUser;

public class LocationListViewPriv extends AppCompatActivity {

    private ListView locationListView;
    private Button modifyLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list_view_priv);
        locationListView = findViewById(R.id.locationList);
        modifyLocationButton = (Button) findViewById(R.id.modifyLocationButton);

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
                // TODO it messing around in listview
//                Collections.sort(locationInfo, new Comparator<Map.Entry<String, String>>() {
//                    @Override
//                    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
//                        return o1.getKey().compareTo(o2.getKey());
//                    }
//                });
                locationListView.setAdapter(new DataListAdapter(locationInfo, getLayoutInflater()));
                locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Sending information through intent
                        Location l = locationList.get(position);
                        Intent detail = new Intent(
                                LocationListViewPriv.this, LocationDetail.class);
                        detail.putExtra("LOCATION", l);
                        startActivity(detail);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        modifyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationListViewPriv.this, DonationList_Own.class);
                System.out.println(currentUser.getAssignedLocation());
                intent.putExtra("DEFAULT", currentUser.getAssignedLocation());
                startActivity(intent);
            }
        });
    }
}


