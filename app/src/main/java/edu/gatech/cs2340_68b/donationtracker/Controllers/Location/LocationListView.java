package edu.gatech.cs2340_68b.donationtracker.Controllers.Location;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;

public class LocationListView extends AppCompatActivity {

    private ListView locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list_view);
        locationList = findViewById(R.id.locationList);
        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("locations");
        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int count = 0;
                ArrayList<String> locationNamesList = new ArrayList<>();
                ArrayList<String> locationAddressesList = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Location place = snapshot.getValue(Location.class);
                    locationNamesList.add(place.getLocationName());
                    locationAddressesList.add(place.getAddress());
                }

                String[] locationNamesArray = new String[locationNamesList.size()];
                String[] locationAddressesArray = new String[locationAddressesList.size()];
                locationNamesArray = locationNamesList.toArray(locationNamesArray);
                locationAddressesArray = locationAddressesList.toArray(locationAddressesArray);
                locationList.setAdapter(new dataListAdapter(
                        locationNamesArray,
                        locationAddressesArray
                ));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    class dataListAdapter extends BaseAdapter {
        String[] Title, Detail;
//        int[] imge;

        dataListAdapter() {
            Title = null;
            Detail = null;
//            imge=null;
        }

        public dataListAdapter(String[] text, String[] text1) {
            Title = text;
            Detail = text1;
//            imge = text3;

        }

        public int getCount() {
            // TODO Auto-generated method stub
            return Title.length;
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
//            ImageView i1;
            title = (TextView) row.findViewById(R.id.title);
            detail = (TextView) row.findViewById(R.id.detail);
//            i1=(ImageView)row.findViewById(R.id.img);
            title.setText(Title[position]);
            detail.setText(Detail[position]);
//            i1.setImageResource(imge[position]);

            return (row);
        }
    }
}


