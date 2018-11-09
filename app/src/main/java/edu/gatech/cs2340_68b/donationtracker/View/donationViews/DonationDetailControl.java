package edu.gatech.cs2340_68b.donationtracker.View.donationViews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.currentUser;

/**
 * Control donation detail page, get data from database
 */

public class DonationDetailControl extends AppCompatActivity {
    private EditText time;
    private TextView location;
    private EditText fullDescription;
    private EditText shortDescription;
    private EditText value;
    private EditText comment;
    private EditText name;
    private Spinner category;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    // --Commented out by Inspection (11/7/18, 10:49 AM):private DatabaseReference ref = database.getReference("donations");

    // Needs DATA, KEY, LOCATION to work properly

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_detail);

//        final String[] arrayOutput = (String[]) getIntent().getSerializableExtra("DATA");
        final DonationDetail donation = (DonationDetail) getIntent().getSerializableExtra("DATA");
        final String keyUsed = (String) getIntent().getSerializableExtra("KEY");
        final String locationUsed = (String) getIntent().getSerializableExtra("LOCATION");

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
        if ((currentUser.getAssignedLocation() == null)
                || !currentUser.getAssignedLocation().
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
                        item.setTime(time.getText().toString());
                        item.setLocation(location.getText().toString());
                        item.setCategory(category.getSelectedItem().toString());
                        item.setFullDescription(fullDescription.getText().toString());
                        item.setShortDescription(shortDescription.getText().toString());
                        item.setValue(value.getText().toString());
                        item.setComment(comment.getText().toString());
                        item.setName(name.getText().toString());
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
                if (spinner.getItemAtPosition(i).toString().equals(compareString)) {
                    return i;
                }
            }
            return -1;
        }
    }
}