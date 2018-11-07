package edu.gatech.cs2340_68b.donationtracker.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import edu.gatech.cs2340_68b.donationtracker.Controllers.HttpUtils;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.Models.UserSearch;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationMap;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Location.LocationControl;

/**
 * The welcome page controller for the app
 */

public class Welcome extends AppCompatActivity {

    private ImageButton imageButton;
    public static User currentUser = new User();
    public static String userKey;
    public static Gson gson = new Gson();
    public static ObjectMapper mapper = new ObjectMapper();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        Button loginButton = findViewById(R.id.LoginButton);
        Button registerButton = findViewById(R.id.RegisterButton);

        Button testButton = findViewById(R.id.TestButton);
        testButton.setVisibility(View.VISIBLE);

        // Adding location to database
        LocationControl a = new LocationControl(this);
        a.readCSVFile();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Login.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
