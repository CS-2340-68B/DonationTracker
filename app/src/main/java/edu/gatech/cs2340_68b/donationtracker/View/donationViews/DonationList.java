package edu.gatech.cs2340_68b.donationtracker.View.donationViews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    DonationDetail detail = snapshot.getValue(DonationDetail.class);
                    donationList.add(detail);
                    Map.Entry<String,String> entry =
                        new AbstractMap.SimpleEntry<>(detail.getName(), detail.getFullDescription());
                    donationInfo.add(entry);
                }
                donationListView.setAdapter(new DataListAdapter(donationInfo, getLayoutInflater()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
