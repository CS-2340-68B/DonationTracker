package edu.gatech.cs2340_68b.donationtracker.View.donationViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import android.view.View;
import android.widget.Button;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.DataListAdapter;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.R;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.currentUser;

public class DonationList extends AppCompatActivity {

    private ListView donationListView;
    private String newLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_list);
        final String locationName = (String) getIntent().getSerializableExtra("PLACENAME");
        final String defaultLocation = (String) getIntent().getSerializableExtra("DEFAULT");
        donationListView = findViewById(R.id.donationList);
        Button addButton = findViewById(R.id.add_button);

        if (locationName == null) {
            newLocation = defaultLocation;
        } else {
            newLocation = locationName;
        }
        DatabaseReference donationDB = FirebaseDatabase.getInstance().getReference("donations/");
        Query query = donationDB.orderByChild("location").equalTo(newLocation);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    (findViewById(R.id.noItemTextView)).setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    (findViewById(R.id.noItemTextView)).setVisibility(View.GONE);
                }
                ArrayList<Map.Entry<String, String>> donationInfo = new ArrayList<>();
                final ArrayList<DonationDetail> donationList = new ArrayList<>();
                final ArrayList<String> keyHashFromFB = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    DonationDetail detail = snapshot.getValue(DonationDetail.class);
                    keyHashFromFB.add(snapshot.getKey());
                    donationList.add(detail);
                    Map.Entry<String,String> entry =
                        new AbstractMap.SimpleEntry<>(
                                Objects.requireNonNull(detail)
                                        .getName(), detail
                                .getFullDescription());
                    donationInfo.add(entry);
                }
                donationListView.setAdapter(new DataListAdapter(donationInfo, getLayoutInflater()));
                donationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        // Sending information through intent
                        DonationDetail donation = donationList.get(position);
                        String keyUsed = keyHashFromFB.get(position);
                        Intent detail = new Intent(DonationList.this, DonationDetailControl.class);
                        detail.putExtra("DATA", donation);
                        detail.putExtra("KEY", keyUsed);
                        detail.putExtra("LOCATION", locationName);
                        startActivity(detail);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}

            });

        //Turn invisible add button if the
        // current user is Admin and or user and locationemployee (not current location)
        if ((currentUser.getAssignedLocation() == null)
                || !currentUser.getAssignedLocation().
                equals(newLocation)) {

            addButton.setVisibility(View.INVISIBLE);
        }


        /**
         * ADD button implementation
         */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("New key: ", newLocation);
                Intent detail = new Intent(DonationList.this, DonationDetailControl.class);
                detail.putExtra("LOCATION", newLocation);
                detail.putExtra("KEY",
                        FirebaseDatabase.getInstance()
                                .getReference(
                                        "donations/").push().getKey());
                detail.putExtra("DATA",
                        new DonationDetail(newLocation));
                startActivity(detail);
            }
        });

    }
}
