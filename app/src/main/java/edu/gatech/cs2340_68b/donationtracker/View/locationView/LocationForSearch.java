package edu.gatech.cs2340_68b.donationtracker.View.locationView;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.SearchResult;

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
                locationListViewForSearch.setAdapter(new dataListAdapter(locationInfo));
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
    class dataListAdapter extends BaseAdapter {
        ArrayList<Map.Entry<String, String>> data;

        dataListAdapter() {
            data = null;
        }

        public dataListAdapter(ArrayList<Map.Entry<String, String>> data) {
            this.data = data;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.list_view_layout, parent, false);
            TextView title, detail;
            title = (TextView) row.findViewById(R.id.title);
            detail = (TextView) row.findViewById(R.id.detail);
            title.setText(data.get(position).getKey());
            detail.setText(data.get(position).getValue());

            return (row);
        }
    }
}


