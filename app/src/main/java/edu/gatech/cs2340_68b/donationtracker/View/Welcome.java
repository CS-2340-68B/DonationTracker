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

public class Welcome extends AppCompatActivity {

    private ImageButton imageButton;
    private Button loginButton;
    private Button registerButton;
    private ActionBar actionBar;
    private Button testButton;
    public static User currentUser = new User();
    public static String userKey;
    public static Gson gson = new Gson();
    public static ObjectMapper mapper = new ObjectMapper();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

//        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        loginButton = findViewById(R.id.LoginButton);
        registerButton = findViewById(R.id.RegisterButton);

        testButton = findViewById(R.id.TestButton);
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

//        testButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RequestParams body = new RequestParams();
////                para.put("username", "Tuan");
////                para.put("password", "123456");
//                body.put("criteria", mapper.convertValue(new Location(), Location.class));
//                HttpUtils.post("/test", body, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        Log.e("Object", "456");
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                        // Pull out the first event on the public timeline
////                        Log.e("Array", timeline.toString());
//                        Location[] locations = gson.fromJson(response.toString(), Location[].class);
//                        for (int i = 0; i < locations.length; i++) {
//                            Log.e("Index: ", "" + i);
//                            Log.e("Location: ", locations[i].getAddress());
//                        }
//                    }
//                });
//            }
//        });
//        String url = "<your site url>";
//        JSONObject jdata = new JSONObject();
//        try {
//            jdata.put("key1", "Tuan");
//            jdata.put("key2", "Nguyen");
//        } catch (Exception ex) {
//            // json exception
//        }
//        StringEntity entity;
//        try {
//            entity = new StringEntity(jdata.toString());
//            new AsyncHttpClient().post(getBaseContext(), url, entity, "application/json", new AsyncHttpResponseHandler() {
//                @Override
//                public void onSuccess(String response) {
//                    JSONObject res;
//                    try {
//                        res = new JSONObject(response);
//                        Log.d("debug", res.getString("some_key")); // this is how you get a value out
//                    } catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//                }
//            });
//        } catch (UnsupportedEncodingException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
    }
}
