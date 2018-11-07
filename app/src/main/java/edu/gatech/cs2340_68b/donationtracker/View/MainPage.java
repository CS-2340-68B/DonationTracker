package edu.gatech.cs2340_68b.donationtracker.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import cz.msebera.android.httpclient.Header;
import edu.gatech.cs2340_68b.donationtracker.Controllers.HttpUtils;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationListView;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchView;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.currentUser;
import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.gson;
import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.mapper;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        setContentView(R.layout.main_page);
        Button logout = (Button) findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button locationList = (Button) findViewById(R.id.donationLocationListButton);
        (findViewById(R.id.menu)).setVisibility(View.INVISIBLE);
        locationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, LocationListView.class);
                startActivity(intent);

            }
        });

        Button userProfile = (Button) findViewById(R.id.profileButton);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, UserProfile.class);
                startActivity(intent);
            }
        });
//        actionBar = getSupportActionBar();

        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, SearchView.class);
                startActivity(intent);
            }
        });

        Button menuT = (Button) findViewById(R.id.menu);
        menuT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Menu.class);
                startActivity(intent);
            }
        });

    }
}

