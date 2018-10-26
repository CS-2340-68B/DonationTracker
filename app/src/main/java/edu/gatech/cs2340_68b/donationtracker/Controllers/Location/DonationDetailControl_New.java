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
    private DatabaseReference myRef = database.getReference("donations");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        setContentView(R.layout.donation_detail_new);
        actionBar = getSupportActionBar();

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




                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DonationDetail item = new DonationDetail();
                        item.setTime(timeI);
                        item.setLocation(locationI);
                        item.setFullDescription(fullDescriptionI);
                        item.setCategory(type);
                        item.setShortDescription(shortDescriptionI);
                        item.setValue(valueI);
                        item.setComment(commentI);
                        DatabaseReference newRef = myRef.push();
                        newRef.setValue(item);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                Intent detail = new Intent(DonationDetailControl_New.this, DonationList_Own.class);
                startActivity(detail);
            }
        });

    }
}
