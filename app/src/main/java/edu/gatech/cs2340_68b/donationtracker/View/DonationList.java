package edu.gatech.cs2340_68b.donationtracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;

public class DonationList extends AppCompatActivity {

    private ListView donationListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_list);

        donationListView = findViewById(R.id.donationList);

        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("donations");
        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Map.Entry<String, String>> donationInfo = new ArrayList<>();
                final ArrayList<DonationDetail> donationList = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    DonationDetail detail = snapshot.getValue(DonationDetail.class);
                    donationList.add(detail);
                    Map.Entry<String,String> entry =
                            new AbstractMap.SimpleEntry<>(detail.getName(), detail.getLocation());
                    donationInfo.add(entry);
                }
                Collections.sort(donationInfo, new Comparator<Map.Entry<String, String>>() {
                    @Override
                    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                        return o1.getKey().compareTo(o2.getKey());
                    }
                });
//                Collections.sort(donationList, new Comparator<Location>() {
//                    @Override
//                    public int compare(Location o1, Location o2) {
//                        return o1.getLocationName().compareTo(o2.getLocationName());
//                    }
//                });
//                donationListView.setAdapter(new dataListAdapter(donationInfo));
                donationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Sending information through intent
                        DonationDetail l = donationList.get(position);
                        Intent detail = new Intent(DonationList.this, LocationDetail.class);
//                        detail.putExtra("LOCATION", l);
                        startActivity(detail);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
