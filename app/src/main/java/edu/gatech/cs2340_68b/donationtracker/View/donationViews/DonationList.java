package edu.gatech.cs2340_68b.donationtracker.View.donationViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.Welcome;

public class DonationList extends AppCompatActivity {

    private ListView donationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_list);
        final String locatioName = (String) getIntent().getSerializableExtra("PLACENAME");
        donationListView = findViewById(R.id.donationList);

        DatabaseReference donationDB = FirebaseDatabase.getInstance().getReference("donations/");
        Query query = donationDB.orderByChild("location").equalTo(locatioName);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Map.Entry<String, String>> donationInfo = new ArrayList<>();
                final ArrayList<DonationDetail> donationList = new ArrayList<>();
                final ArrayList<String> keyHashFromFB = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    DonationDetail detail = snapshot.getValue(DonationDetail.class);
                    keyHashFromFB.add(snapshot.getKey());
                    donationList.add(detail);
                    Map.Entry<String,String> entry =
                        new AbstractMap.SimpleEntry<>(detail.getName(), detail.getFullDescription());
                    donationInfo.add(entry);
                }
                donationListView.setAdapter(new DataListAdapter(donationInfo, getLayoutInflater()));
                donationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Sending information through intent
                        DonationDetail l = donationList.get(position);
                        String keyUsed = keyHashFromFB.get(position);
                        String array[] = new String[8];
                        array[0] = l.getName();
                        array[1] = l.getCategory();
                        array[2] = l.getComment();
                        array[3] = l.getFullDescription();
                        array[4] = l.getLocation();
                        array[5] = l.getShortDescription();
                        array[6] = l.getTime();
                        array[7] = l.getValue();
                        Intent detail = new Intent(DonationList.this, DonationDetailControl.class);
                        detail.putExtra("DATA", array);
                        detail.putExtra("KEY", keyUsed);
                        startActivity(detail);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
