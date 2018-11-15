package edu.gatech.cs2340_68b.donationtracker.View.donationViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import edu.gatech.cs2340_68b.donationtracker.View.Login;
import edu.gatech.cs2340_68b.donationtracker.View.UserProfile;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationListView;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchHistory;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchView;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.currentUser;
import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.gson;

/**
 * Get data from database and put to donation list
 */

@SuppressWarnings({"FeatureEnvy", "ChainedMethodCall",
        "ConstantConditions", "ClassWithTooManyDependencies"})
public class DonationList extends AppCompatActivity {

    private ListView donationListView;
    private String newLocation;
    private ActionBarDrawerToggle aToggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_list);
        final Context contect = this;

        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Toolbar aToolbar = findViewById(R.id.nav_actionbar);
        setSupportActionBar(aToolbar);
        aToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(aToggle);
        aToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        if (menuItem.getItemId() == R.id.nav_account) {
                            Intent intent = new Intent(contect ,UserProfile.class);
                            startActivity(intent);
                        }
                        if (menuItem.getItemId() == R.id.nav_search) {
                            Intent intent = new Intent(contect ,SearchView.class);
                            startActivity(intent);
                        }
                        if (menuItem.getItemId() == R.id.nav_location) {
                            Intent intent = new Intent(contect ,LocationListView.class);
                            startActivity(intent);
                        }
                        if (menuItem.getItemId() == R.id.nav_history) {
                            Intent intent = new Intent(contect ,SearchHistory.class);
                            startActivity(intent);
                        }
                        if (menuItem.getItemId() == R.id.nav_logout) {
                            Intent intent = new Intent(contect, Login.class);
                            startActivity(intent);
                        }



                        // close drawer when item is tapped
                        drawer.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

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
                    TextView tv = findViewById(R.id.noItemTextView);
                    tv.setVisibility(View.VISIBLE);
                } else {
                    TextView tv2 = (findViewById(R.id.noItemTextView));
                    tv2.setVisibility(View.GONE);
                    ArrayList<Map.Entry<String, String>> donationInfo = new ArrayList<>();
                    final List<DonationDetail> donationList = new ArrayList<>();
                    final List<String> keyHashFromFB = new ArrayList<>();
                    for (DonationDetail detail : donations) {
                        keyHashFromFB.add(detail.getDonationKey());
                        donationList.add(detail);
                        Map.Entry<String, String> entry =
                                new AbstractMap.SimpleEntry<>(
                                        Objects.requireNonNull(detail)
                                                .getName(), detail
                                        .getFullDescription());
                        donationInfo.add(entry);
                    }
                    donationListView.setAdapter(new DataListAdapter
                            (donationInfo, getLayoutInflater()));
                    donationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(
                                AdapterView<?> parent, View view, int position, long id) {
                            // Sending information through intent
                            DonationDetail donation = donationList.get(position);
                            String keyUsed = keyHashFromFB.get(position);
                            Intent detail = new Intent(
                                    DonationList.this,
                                    DonationDetailControl.class);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_account) {
            Intent intent = new Intent(this,UserProfile.class);
            this.startActivity(intent);
            return true;

        }
        if (aToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
}
