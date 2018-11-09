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
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.view.View;
import android.widget.Button;

import org.json.JSONArray;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.DataListAdapter;
import edu.gatech.cs2340_68b.donationtracker.Controllers.HttpUtils;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.R;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.currentUser;
import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.gson;

/**
 * Get data from database and put to donation list
 */

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

        RequestParams params = new RequestParams("locationName", locationName);
        HttpUtils.postForm("/getDonations", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                DonationDetail[] donations = gson
                        .fromJson(response.toString(), DonationDetail[].class);
                if (donations.length == 0) {
                    (findViewById(R.id.noItemTextView)).setVisibility(View.VISIBLE);
                } else {
                    (findViewById(R.id.noItemTextView)).setVisibility(View.GONE);
                    ArrayList<Map.Entry<String, String>> donationInfo = new ArrayList<>();
                    final List<DonationDetail> donationList = new ArrayList<>();
                    final List<String> keyHashFromFB = new ArrayList<>();
                    for (int i = 0; i < donations.length; i++) {
                        DonationDetail detail = donations[i];
                        keyHashFromFB.add(detail.getDonationKey());
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
            }
        });

        //Turn invisible add button if the
        // current user is Admin and or user and locationemployee (not current location)
        if ((currentUser.getAssignedLocation() == null)
                || !currentUser.getAssignedLocation().
                equals(newLocation)) {

            addButton.setVisibility(View.INVISIBLE);
        }


        /*
          ADD button implementation
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
