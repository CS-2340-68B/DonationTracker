package edu.gatech.cs2340_68b.donationtracker.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340_68b.donationtracker.R;

public class DonationList_Own extends AppCompatActivity{

    private ListView donationListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_list_own);
        final String donation = (String) getIntent().getSerializableExtra("PLACENAME");
        donationListView = findViewById(R.id.donationList);

        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("donations/" + donation);
        locationDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
