package edu.gatech.cs2340_68b.donationtracker.View.locationViews;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.DataListAdapter;
import edu.gatech.cs2340_68b.donationtracker.Controllers.HttpUtils;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.UserProfile;
import edu.gatech.cs2340_68b.donationtracker.View.Login;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchHistory;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchView;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.gson;

/**
 * Control the view of location list
 */
public class LocationListView extends AppCompatActivity {

    private ListView locationListView;
    private Button mapButton;
    private ActionBarDrawerToggle aToggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_list_view);

        locationListView = findViewById(R.id.locationList);
        mapButton = findViewById(R.id.MapButton);

        final Context contect = this;

        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Toolbar aToolbar = findViewById(R.id.nav_actionbar);
        setSupportActionBar(aToolbar);
        DrawerLayout nDrawerLayout = findViewById(R.id.drawerLayout);
        aToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open, R.string.close);
        nDrawerLayout.addDrawerListener(aToggle);
        aToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
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
                        if (menuItem.getItemId() == R.id.nav_history) {
                            Intent intent = new Intent(contect ,SearchHistory.class);
                            startActivity(intent);
                        }
                        if (menuItem.getItemId() == R.id.nav_logout) {
//                            finish();
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


        // Get locations from firebase
        DatabaseReference locationDB = FirebaseDatabase.getInstance().getReference("locations");

        HttpUtils.get("/getLocations", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                ArrayList<Map.Entry<String, String>> locationInfo = new ArrayList<>();
                final ArrayList<Location> locationList = new ArrayList<>();
                Location[] locations = gson.fromJson(response.toString(), Location[].class);
                for (int i = 0; i < locations.length; i++) {
                    locationList.add(locations[i]);
                    Map.Entry<String,String> entry =
                            new AbstractMap.SimpleEntry<>(
                                    Objects.requireNonNull(locations[i]).getLocationName(),
                                    locations[i].getAddress());
                    locationInfo.add(entry);
                }
                Collections.sort(locationInfo, new Comparator<Map.Entry<String, String>>() {
                    @Override
                    public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                        return o1.getKey().compareTo(o2.getKey());
                    }
                });
                Collections.sort(locationList, new Comparator<Location>() {
                    @Override
                    public int compare(Location o1, Location o2) {
                        return o1.getLocationName().compareTo(o2.getLocationName());
                    }
                });
                locationListView.setAdapter(new DataListAdapter(locationInfo, getLayoutInflater()));
                locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Sending information through intent
                        Location l = locationList.get(position);
                        Intent detail = new Intent(LocationListView.this, LocationDetail.class);
                        detail.putExtra("LOCATION", l);
                        startActivity(detail);
                    }
                });

                mapButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent locationMapView = new Intent(LocationListView.this, LocationMap.class);
                        locationMapView.putExtra("LocationList", locationList);
                        startActivity(locationMapView);
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (aToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
}


