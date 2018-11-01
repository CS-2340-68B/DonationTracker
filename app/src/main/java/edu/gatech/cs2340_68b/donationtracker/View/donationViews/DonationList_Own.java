package edu.gatech.cs2340_68b.donationtracker.View.donationViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.DataListAdapter;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.Welcome;

public class DonationList_Own extends AppCompatActivity {

    private ListView donationListView;
    private Button addButton;
    private String newLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_list_own);
        final String locationName = (String) getIntent().getSerializableExtra("PLACENAME");
        final String defaultLocation = (String) getIntent().getSerializableExtra("DEFAULT");
        donationListView = findViewById(R.id.donationList);
        addButton = findViewById(R.id.add_button);

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
//                Collections.sort(donationInfo, new Comparator<Map.Entry<String, String>>() {
//                    @Override
//                    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
//                        return o1.getKey().compareTo(o2.getKey());
//                    }
//                });
//                Collections.sort(donationList, new Comparator<DonationDetail>() {
//                    @Override
//                    public int compare(DonationDetail o1, DonationDetail o2) {
//                        return o1.getName().compareTo(o2.getName());
//                    }
//                });
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
                        Intent detail = new Intent(DonationList_Own.this, DonationDetailControl.class);
                        detail.putExtra("DATA", array);
                        detail.putExtra("KEY", keyUsed);
                        detail.putExtra("LOCATION", locationName);
                        startActivity(detail);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}

            });

        /**
         * Turn invisible add button if the current user is Admin and or user and locationemployee (not current location)
         */
        if (Welcome.currentUser.getType().toString().equals("LOCATIONEMPLOYEE")) {
            if (Welcome.currentUser.getAssignedLocation().toString().equals(newLocation)) {
                addButton.setVisibility(View.VISIBLE);
            } else {
                addButton.setVisibility(View.INVISIBLE);
            }
        } else if (Welcome.currentUser.getType().toString().equals("MANAGER")) {
            addButton.setVisibility(View.VISIBLE);
        }
        else if (Welcome.currentUser.getType().toString().equals("ADMIN") || Welcome.currentUser.getType().toString().equals("USER")) {
            addButton.setVisibility(View.INVISIBLE);
        }


        /**
         * ADD button implementation
         */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationList_Own.this, DonationDetailControl_New.class);
                intent.putExtra("LOCATION", newLocation);
                startActivity(intent);
            }
        });

    }
}
