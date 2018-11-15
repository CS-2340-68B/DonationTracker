package edu.gatech.cs2340_68b.donationtracker.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.PasswordEncryption;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.VerifyFormat;
import edu.gatech.cs2340_68b.donationtracker.Controllers.HttpUtils;
import edu.gatech.cs2340_68b.donationtracker.Models.Location;
import edu.gatech.cs2340_68b.donationtracker.Models.Response;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.Models.Enum.UserType;
import edu.gatech.cs2340_68b.donationtracker.R;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.gson;

/**
 * Controller for register page, user can create a new account
 * A new account is added to database
 */

@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "FeatureEnvy"})
public class Register extends AppCompatActivity {

    private TextView usernameTV;
    private TextView passwordTV;
    private TextView confirmPasswordTV;
    private Spinner utspinner;
    private Spinner locspinner;
    private final User newAccount = new User();

    private final int ADMIN = 0;
    private final int USER = 1;
    private final int LOCATIONEMPLOYEE = 2;
    private final int MANAGER = 3;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout .register);

        usernameTV = findViewById(R.id.registerUsername);
        passwordTV = findViewById(R.id.registerPassword);
        confirmPasswordTV = findViewById(R.id.registerConfirmPassword);
        Button register = findViewById(R.id.register);
        Button cancel = findViewById(R.id.cancel);
        utspinner = findViewById(R.id.userTypeSpinner);
        locspinner = findViewById(R.id.locationSpinner);



        // Read in location
        final List<Location> locationList = new ArrayList<>();
        final List<String> locationListString = new ArrayList<>();
        final Context self = this;
        // Get values from locations
        HttpUtils.get("/getLocations", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Collection<Map.Entry<String, String>> locationInfo = new ArrayList<>();
                Location[] locations = gson.fromJson(response.toString(), Location[].class);
                for (Location location : locations) {
                    locationList.add(location);
                    locationListString.add(location.getLocationName());
                    Map.Entry<String, String> entry =
                            new AbstractMap.SimpleEntry<>(
                                    location.getLocationName(), location.getAddress());
                    locationInfo.add(entry);

                     /*
                      Set up the adapter to display the locations in the spinner
                     */
                    ArrayAdapter<String> adapter2 = new ArrayAdapter(
                            self, android.R.layout.simple_spinner_item, locationListString);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    locspinner.setAdapter(adapter2);
                }
            }
        });

        /*
          Set up the adapter to display the user types in the spinner
         */
        ArrayAdapter<String> adapter1 = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item,
                UserType.values());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        utspinner.setAdapter(adapter1);

        // UT Listener
        utspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(
                    AdapterView<?> parentView,
                    View selectedItemView, int position, long id) {
                switch (position) {
                    case USER:
                        newAccount.setType(UserType.USER);
                        newAccount.setAssignedLocation(null);
                        locspinner.setVisibility(View.GONE);
                        break;
                    case LOCATIONEMPLOYEE:
                        newAccount.setType(UserType.LOCATIONEMPLOYEE);
                        locspinner.setVisibility(View.VISIBLE);
                        //newAccount.setAssignedLocation(locspinner.getSelectedItem().toString());
                        break;
                    case ADMIN:
                        newAccount.setType(UserType.ADMIN);
                        newAccount.setAssignedLocation(null);
                        locspinner.setVisibility(View.GONE);
                        break;
                    case MANAGER:
                        newAccount.setType(UserType.MANAGER);
                        locspinner.setVisibility(View.VISIBLE);
                        //newAccount.setAssignedLocation(locspinner.getSelectedItem().toString());
                        break;
                    default:
                        newAccount.setType(UserType.USER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        // Location Listener
        locspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView,
                                       int position,
                                       long id) {
                newAccount.setAssignedLocation(locationList.get(position).getLocationName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameTV.getText().toString();
                final String password = passwordTV.getText().toString();
                final UserType type = (UserType) utspinner.getSelectedItem();
                final String locationName = locspinner.getSelectedItem().toString();
                String confirmPassword = confirmPasswordTV.getText().toString();

                if (!VerifyFormat.verifyEmailFormat(username)) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Error", "User account format is not correct.");
                    alert.create().show();
                }

                else if (!password.equals(confirmPassword)) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Error", "Password are not the same.");
                    alert.create().show();
                }

                else if (!VerifyFormat.verifyPassword(password)) {
                    AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                            "Error", "Your password must " +
                                    "contain at least 1 letter, 1 number, and " +
                            "one upper case letter, and be at least 8 characters long");
                    alert.create().show();
                }

                else {

                    RequestParams query =  new RequestParams();
                    query.put("username", username);
                    query.put("password", PasswordEncryption.encode(password));
                    query.put("userType", type);
                    query.put("locationName", locationName);
                    HttpUtils.postForm("/register", query, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(
                                int statusCode, Header[] headers,
                                JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Response res = gson.fromJson(response.toString(), Response.class);
                            if ("fail".equals(res.status)) {
                                AlertDialog.Builder alert = CustomDialog.errorDialog(Register.this,
                                        "Registration Error", "Username already exists");
                                alert.create().show();
                            } else {
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
