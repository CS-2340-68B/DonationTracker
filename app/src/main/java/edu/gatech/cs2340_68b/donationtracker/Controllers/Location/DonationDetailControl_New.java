package edu.gatech.cs2340_68b.donationtracker.Controllers.Location;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340_68b.donationtracker.Models.Category;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.Models.Enum.UserType;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.DonationList_Own;
import edu.gatech.cs2340_68b.donationtracker.View.LocationListViewPriv;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.currentUser;

public class DonationDetailControl_New extends AppCompatActivity {
    private ActionBar actionBar;
    private EditText time;
    private EditText location;
    private EditText fullDescription;
    private EditText shortDescription;
    private EditText value;
    private EditText comment;
    private Spinner category;
    private Button submit;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_detail_new);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        actionBar = getSupportActionBar();

        final String donation = (String) getIntent().getSerializableExtra("LOCATION");
        System.out.println("LINE             : " + donation);
        final DatabaseReference myRef = database.getReference("donations/" + donation);
        time = (EditText) findViewById(R.id.timeInput);
        location = (EditText) findViewById(R.id.locationDonationEdit);
        fullDescription = (EditText) findViewById(R.id.fullDescriptionEdit);
        shortDescription = (EditText) findViewById(R.id.shortDescriptionEdit);
        value = (EditText) findViewById(R.id.valueEdit);
        comment = (EditText) findViewById(R.id.commentEdit);
        category = (Spinner)findViewById(R.id.categorySpinner);
        submit = (Button) findViewById(R.id.submit);


        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String timeI = time.getText().toString();
                final String locationI = location.getText().toString();
                final Category type = (Category) category.getSelectedItem();
                final String fullDescriptionI = fullDescription.getText().toString();
                final String shortDescriptionI = shortDescription.getText().toString();
                final String valueI = value.getText().toString();
                final String commentI = comment.getText().toString();


                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DonationDetail item = new DonationDetail(timeI, locationI, fullDescriptionI, shortDescriptionI, valueI, category.getSelectedItem().toString(), commentI, donation);
                        DatabaseReference newRef = myRef.push();
                        newRef.setValue(item);
                        Intent detail = new Intent(DonationDetailControl_New.this, LocationListViewPriv.class);
                        startActivity(detail);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) { }
                });
            }
        });

    }
}
