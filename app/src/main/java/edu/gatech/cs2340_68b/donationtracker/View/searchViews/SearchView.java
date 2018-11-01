package edu.gatech.cs2340_68b.donationtracker.View.searchViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;

public class SearchView extends AppCompatActivity {

    // Define Variables
    private RadioGroup searchRadioGroup;
    private Button searchHistoryButton;
    private TextInputEditText searchBar;
    private Button searchButton;
    private Spinner searchLocSpinner;
    private ListView searchResultList;
    private int searchTypeFlag;
    private boolean isSearchAll;
    private Location currentLocation;
    Location allLocations = new Location("All");
    final int SEARCHITEM = 0;
    final int SEARCHCAT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);

        // Initialize components
        searchRadioGroup = (RadioGroup) findViewById(R.id.searchTypeRadioGroup);
        searchHistoryButton = (Button) findViewById(R.id.searchHistoryButton);
        searchBar = (TextInputEditText) findViewById(R.id.searchBar);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchLocSpinner = (Spinner) findViewById(R.id.searchLocSpinner);
        searchResultList = (ListView) findViewById(R.id.searchResultList);

        // Initialize other variables
        searchTypeFlag = -1;
        isSearchAll = true;

        // Radio Group Listener
        searchRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("MYTAG", "Radio Button Working");
                View radioButton = searchRadioGroup.findViewById(checkedId);
                searchTypeFlag = searchRadioGroup.indexOfChild(radioButton);
            }
        });

        // Search History Button Listener
        searchHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MYTAG", "Search History Button Working");
                Intent intent = new Intent(SearchView.this, SearchView.class);
                startActivity(intent);
            }
        });

        // Search Button Listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MYTAG", "Search Button Working");
                String searchString = searchBar.getText().toString();

                switch (searchTypeFlag) {
                    case 0: // search item
                        if (isSearchAll) {

                        } else {

                        }
                        break;
                    case 1: // search categori
                        if (isSearchAll) {

                        } else {

                        }
                        break;

                }
            }
        });

        /*
         * LOCATION SET UP
         */

        // Read in location
        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("locations");
        final ArrayList<Location> locationList = new ArrayList<>();
        final ArrayList<String> locationListString = new ArrayList<>();
        // Get values from locations
        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Map.Entry<String, String>> locationInfo = new ArrayList<>();
                // Loads in all locations into the array list
                locationList.add(allLocations);
                locationListString.add("All");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Create local copy of one location
                    Location place = snapshot.getValue(Location.class);
                    locationList.add(place);
                    locationListString.add(place.getLocationName());
                    Map.Entry<String, String> entry =
                            new AbstractMap.SimpleEntry<>(place.getLocationName(), place.getAddress());
                    locationInfo.add(entry);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // Set up adapter for location spinner
        ArrayAdapter<String> locationAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, locationListString);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchLocSpinner.setAdapter(locationAdapter);

        // Search Location Spinner
        searchLocSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Log.d("MYTAG", "Spinner Item Select Working");
                currentLocation = locationList.get(position);
                if (position == 0) { // First item is All Location
                    isSearchAll = true;
                } else {
                    isSearchAll = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                isSearchAll = true;
                currentLocation = allLocations;
            }
        });


    }
}