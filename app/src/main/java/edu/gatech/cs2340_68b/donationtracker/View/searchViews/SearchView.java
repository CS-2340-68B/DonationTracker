package edu.gatech.cs2340_68b.donationtracker.View.searchViews;

import android.content.Context;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.DataListAdapter;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.Models.Enum.Category;
import edu.gatech.cs2340_68b.donationtracker.Models.Enum.SearchOptions;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.Models.UserSearch;
import edu.gatech.cs2340_68b.donationtracker.R;

public class SearchView extends AppCompatActivity {

    // Define Variables
    private RadioGroup searchRadioGroup;
    private Button searchHistoryButton;
    private TextInputEditText searchBar;
    private Spinner searchCatSpinner;
    private Button searchButton;
    private Spinner searchLocSpinner;
    private ListView searchResultList;
    private int searchTypeFlag;
    private boolean isSearchAll;
    private Location currentLocation;
    private Category currentCat;
    private UserSearch searchCriteria;

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
        searchCatSpinner = (Spinner) findViewById(R.id.searchCatSpinner);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchLocSpinner = (Spinner) findViewById(R.id.searchLocSpinner);
        searchResultList = (ListView) findViewById(R.id.searchResultList);
        searchCriteria = new UserSearch();

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
                if (searchTypeFlag == 0) { // Item search
                    searchCriteria.setSearchOption(SearchOptions.NAME);
                    searchBar.setVisibility(View.VISIBLE);
                    searchCatSpinner.setVisibility(View.GONE);

                } else {                    // Category search
                    searchCriteria.setSearchOption(SearchOptions.CATEGORY);
                    searchBar.setVisibility(View.GONE);
                    searchCatSpinner.setVisibility(View.VISIBLE);
                }
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

                Log.e("Tag: ", searchCriteria.getSearchOption().toString());
                if (searchCriteria.getSearchOption().equals(SearchOptions.NAME)) {
                    searchCriteria.setKeyword(searchBar.getText().toString());
                } else {
                    searchCriteria.setKeyword(searchCatSpinner.getSelectedItem().toString());
                }
                searchCriteria.setLocationName(searchLocSpinner.getSelectedItem().toString());

                DatabaseReference donationDB = FirebaseDatabase.getInstance().getReference("donations");
                Query query = null;

                if (searchCriteria.getLocationName().equals("All")) {
                    if (searchCriteria.getSearchOption().equals(SearchOptions.NAME)) {
                        query = donationDB.orderByChild("name").equalTo(searchCriteria.getKeyword());
                    } else {
                        query = donationDB.orderByChild("category").equalTo(searchCriteria.getKeyword());
                    }
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final ArrayList<Map.Entry<String, String>> donationInfo = new ArrayList<>();
                            final ArrayList<DonationDetail> donationList = new ArrayList<>();
                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                DonationDetail detail = snapshot.getValue(DonationDetail.class);
                                Log.e("Item: ", detail.getName());
                                donationList.add(detail);
                                Map.Entry<String,String> entry =
                                        new AbstractMap.SimpleEntry<>(detail.getName(), detail.getFullDescription());
                                donationInfo.add(entry);
                            }
                            searchResultList.setAdapter(new DataListAdapter(donationInfo, getLayoutInflater()));
//                            searchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                    // Sending information through intent
//                                    DonationDetail l = donationList.get(position);
//                                    Intent detail = new Intent(SearchResult.this, DonationDetail.class);
//                                    startActivity(detail);
//                                }
//                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                Log.e("Object: ", searchCriteria.toString());

                switch (searchTypeFlag) {
                    case 0: // Search Item
                        if (isSearchAll) { // Search location in all locations
                            /* TO BE IMPLEMENTED BY TUAN (?) FROM SEARCHRESULT */
                        } else { // Search location in specific location
                            /* TO BE IMPLEMENTED BY TUAN (?) FROM SEARCHRESULT */
                        }
                        break;
                    case 1: // Search Category
                        if (isSearchAll) { // Search category in all locations
                            /* TO BE IMPLEMENTED BY TUAN (?) FROM SEARCHRESULT */
                        } else { // Search category in specific locations
                            /* TO BE IMPLEMENTED BY TUAN (?) FROM SEARCHRESULT */
                        }
                        break;

                }
            }
        });

        /*
        * CATEGORY SET UP
        */
        // Set up cateogry spinner adapter
        ArrayAdapter<String> catAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Category.values());
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchCatSpinner.setAdapter(catAdapter);
        searchCatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentCat = Category.values()[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                currentCat = Category.values()[0];
            }
        });

        /*
         * LOCATION SET UP
         */

        // Read in location
        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("locations");
        final ArrayList<Location> locationList = new ArrayList<>();
        final ArrayList<String> locationListString = new ArrayList<>();
        final Context self = this;
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

                    // Set up adapter for location spinner
                    ArrayAdapter locationAdapter = new ArrayAdapter(self, android.R.layout.simple_spinner_item, locationListString);
                    locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    searchLocSpinner.setAdapter(locationAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

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
                Log.d("MYTAG", "Spinner Nothing Selected");
                isSearchAll = true;
                currentLocation = allLocations;
            }
        });
    }
}