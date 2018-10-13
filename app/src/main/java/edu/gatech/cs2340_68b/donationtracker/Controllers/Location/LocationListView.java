package edu.gatech.cs2340_68b.donationtracker.Controllers.Location;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.R;

public class LocationListView extends AppCompatActivity {

    private ListView locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list_view);
        final Context context = this;
        locationList = (ListView)findViewById(R.id.locationList);
        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("locations");
        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                ArrayList<String> locationNames = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Location a = snapshot.getValue(Location.class);
                    locationNames.add(a.getLocationName());
                }
                ArrayAdapter adapter = new ArrayAdapter(context,
                        android.R.layout.simple_list_item_1, locationNames);

                locationList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        ArrayList<String> list = new ArrayList<>();
//        list.add("Tuan");
//        list.add("Dep");
//        list.add("Trai");
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_list_item_1, list);
//
//        locationList.setAdapter(arrayAdapter);
    }
}
