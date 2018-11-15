package edu.gatech.cs2340_68b.donationtracker.View.donationViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340_68b.donationtracker.Models.Enum.Category;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.Login;
import edu.gatech.cs2340_68b.donationtracker.View.MainPage;
import edu.gatech.cs2340_68b.donationtracker.View.UserProfile;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationListView;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchHistory;
import edu.gatech.cs2340_68b.donationtracker.View.searchViews.SearchView;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.currentUser;

/**
 * Control donation detail page, get data from database
 */

@SuppressWarnings({"FeatureEnvy", "ChainedMethodCall", "ConstantConditions"})
public class DonationDetailControl extends AppCompatActivity {
    private EditText time;
    private TextView location;
    private EditText fullDescription;
    private EditText shortDescription;
    private EditText value;
    private EditText comment;
    private EditText name;
    private Spinner category;
    private ActionBarDrawerToggle aToggle;
    private DrawerLayout drawer;

    // Needs DATA, KEY, LOCATION to work properly

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_detail);
        //create menu
        final Context contect = this;
        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar aToolbar = findViewById(R.id.nav_actionbar);
        setSupportActionBar(aToolbar);
        aToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(aToggle);
        aToggle.syncState();
        //noinspection ConstantConditions
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
                        if (menuItem.getItemId() == R.id.nav_main) {
                            Intent intent = new Intent(contect ,MainPage.class);
                            startActivity(intent);
                            finish();
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

        Intent var = getIntent();
//        final String[] arrayOutput = (String[]) getIntent().getSerializableExtra("DATA");
        final DonationDetail donation = (DonationDetail) var.getSerializableExtra("DATA");
        final String keyUsed = (String) var.getSerializableExtra("KEY");
        final String locationUsed = (String) var.getSerializableExtra("LOCATION");

        time = findViewById(R.id.timeInput);
        location = findViewById(R.id.locationDonationEdit);
        fullDescription = findViewById(R.id.fullDescriptionEdit);
        shortDescription = findViewById(R.id.shortDescriptionEdit);
        value = findViewById(R.id.valueEdit);
        comment = findViewById(R.id.commentEdit);
        category = findViewById(R.id.categorySpinner);


        Button submit = findViewById(R.id.submit);
        name = findViewById(R.id.nameEdit);

        /*
          Auto fill data into the box fills if the client want to view or edits
         */
        time.setText(donation.getTime());
        location.setText(donation.getLocation());
        fullDescription.setText(donation.getFullDescription());
        shortDescription.setText(donation.getShortDescription());
        value.setText(donation.getValue());
        comment.setText(donation.getComment());
        name.setText(donation.getName());

        ArrayAdapter<String> adapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item,
                Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        /*
          Update the category dropdown base on whatever data show in database
         */
        if (donation.getCategory() != null) {
            int spinnerPosition = getIndexSpinner(category, donation.getCategory());
            if (spinnerPosition != -1) {
                category.setSelection(spinnerPosition);
            }
        }


        //Limit only LOCATIONEMPLOYEE (of register
        // location) and Manager are allow to edit the details
        String loc = currentUser.getAssignedLocation();
        if ((currentUser.getAssignedLocation() == null)
                || !loc.
                equals(donation.getLocation())) {
            time.setKeyListener(null);
            location.setKeyListener(null);
            name.setKeyListener(null);
            comment.setKeyListener(null);
            location.setKeyListener(null);
            value.setKeyListener(null);
            shortDescription.setKeyListener(null);
            fullDescription.setKeyListener(null);
            category.setEnabled(false);
            submit.setVisibility(View.INVISIBLE);
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseDatabase Firebase = FirebaseDatabase.getInstance();
                final DatabaseReference ref = Firebase.getReference("donations").child(keyUsed);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.e("Result: ", "Found item");
                        DonationDetail item = dataSnapshot.getValue(DonationDetail.class);
                        if (item == null) {
                            item = new DonationDetail(locationUsed);
                        }
                        Editable timetext = time.getText();
                        item.setTime(timetext.toString());
                        CharSequence loctest = location.getText();
                        item.setLocation(loctest.toString());
                        Object cat = category.getSelectedItem();
                        item.setCategory(cat.toString());
                        Object fd = fullDescription.getText();
                        item.setFullDescription(fd.toString());
                        Object sd = shortDescription.getText();
                        item.setShortDescription(sd.toString());
                        Object val = value.getText();
                        item.setValue(val.toString());
                        Object com = comment.getText();
                        item.setComment(com.toString());
                        Object nametxt = name.getText();
                        item.setName(nametxt.toString());
                        ref.setValue(item);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
        });
    }

    /**
     * Helper method to find out the index that store the value in enum
     * @param spinner The spinner array list of enum
     * @param compareString The string that hold the data
     * @return The index of location of that enum
     */
    private int getIndexSpinner(Spinner spinner, String compareString) {
        if ((compareString == null) || (spinner.getCount() == 0)) {
            return -1;
        } else {
            for (int i = 0; i < spinner.getCount(); i++) {
                Object pos = spinner.getItemAtPosition(i);
                String posString = pos.toString();
                if (posString.equals(compareString)) {
                    return i;
                }
            }
            return -1;
        }
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