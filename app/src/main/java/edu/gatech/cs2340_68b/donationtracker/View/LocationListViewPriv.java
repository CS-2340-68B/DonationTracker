package edu.gatech.cs2340_68b.donationtracker.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class LocationListViewPriv extends AppCompatActivity {

    private ListView locationListView;
    private ActionBar actionBar;
    private Button modifyLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list_view);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        locationListView = findViewById(R.id.locationList);
        modifyLocationButton = (Button) findViewById(R.id.modifyLocationButton);

        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("locations");
        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Map.Entry<String, String>> locationInfo = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Location place = snapshot.getValue(Location.class);
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
                locationListView.setAdapter(new dataListAdapter(locationInfo));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        modifyLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationListViewPriv.this, DonationList.class);
                startActivity(intent);
            }
        });
    }
    class dataListAdapter extends BaseAdapter {
//        String[] Title, Detail;
        ArrayList<Map.Entry<String, String>> data;
//        int[] imge;

        dataListAdapter() {
//            Title = null;
//            Detail = null;
//            imge=null;
            data = null;
        }

//        public dataListAdapter(String[] text, String[] text1) {
//            Title = text;
//            Detail = text1;
//            imge = text3;
//        }

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
//            ImageView i1;
            title = (TextView) row.findViewById(R.id.title);
            detail = (TextView) row.findViewById(R.id.detail);
//            i1=(ImageView)row.findViewById(R.id.img);
//            title.setText(Title[position]);
//            detail.setText(Detail[position]);
//            i1.setImageResource(imge[position]);
            title.setText(data.get(position).getKey());
            detail.setText(data.get(position).getValue());

            return (row);
        }
    }
}


