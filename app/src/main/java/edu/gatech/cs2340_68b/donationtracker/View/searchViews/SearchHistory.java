package edu.gatech.cs2340_68b.donationtracker.View.searchViews;

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
import edu.gatech.cs2340_68b.donationtracker.Models.UserSearch;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.Welcome;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationDetail;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationListView;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationMap;

public class SearchHistory extends AppCompatActivity {

    private ListView searchHistoryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_history);

        searchHistoryListView = findViewById(R.id.searchHistoryListView);

        // Define firebase instance to get search histories
        DatabaseReference historyDB = FirebaseDatabase.getInstance()
                .getReference("accounts").child(Welcome.userKey).child("userSearchList");

        // Get list of search history from firebase
        historyDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Map.Entry<String, String>> searchInfo = new ArrayList<>();
                final ArrayList<UserSearch> searchList = new ArrayList<>();

                // Loads in all searches into the array list
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    // Create local copy of one search criteria
                    UserSearch search = snapshot.getValue(UserSearch.class);
                    searchList.add(search);
                    Map.Entry<String,String> entry =
                            new AbstractMap.SimpleEntry<>(
                                    search.getSearchOption() + ": " + search.getKeyword(),
                                    "Location: " + search.getLocationName());
                    searchInfo.add(entry);
                }
                Collections.reverse(searchInfo);
                searchHistoryListView.setAdapter(new DataListAdapter(searchInfo, getLayoutInflater()));
//                searchHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        // Sending information through intent
//                        Location l = locationList.get(position);
//                        Intent detail = new Intent(LocationListView.this, LocationDetail.class);
//                        detail.putExtra("LOCATION", l);
//                        startActivity(detail);
//                    }
//                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}