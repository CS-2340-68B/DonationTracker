package edu.gatech.cs2340_68b.donationtracker.View.donationViews;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340_68b.donationtracker.Models.Category;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.Models.Enum.UserType;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.locationViews.LocationListViewPriv;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.currentUser;

public class DonationDetailControl extends AppCompatActivity {
    private ActionBar actionBar;
    private EditText time;
    private EditText location;
    private EditText fullDescription;
    private EditText shortDescription;
    private EditText value;
    private EditText comment;
    private EditText name;
    private Spinner category;
    private Button submit;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("donations");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        setContentView(R.layout.donation_detail);
        actionBar = getSupportActionBar();

        final String[] arrayOutput = (String[]) getIntent().getSerializableExtra("DATA");
        final String keyUsed = (String) getIntent().getSerializableExtra("KEY");
        final String locationUsed = (String) getIntent().getSerializableExtra("LOCATION");

        System.out.println(keyUsed);

        time = (EditText) findViewById(R.id.timeInput);
        location = (EditText) findViewById(R.id.locationDonationEdit);
        fullDescription = (EditText) findViewById(R.id.fullDescriptionEdit);
        shortDescription = (EditText) findViewById(R.id.shortDescriptionEdit);
        value = (EditText) findViewById(R.id.valueEdit);
        comment = (EditText) findViewById(R.id.commentEdit);
        category = (Spinner)findViewById(R.id.categorySpinner);
        submit = (Button) findViewById(R.id.submit);
        name = (EditText) findViewById(R.id.nameEdit);

        time.setText(arrayOutput[6]);
        location.setText(arrayOutput[4]);
        fullDescription.setText(arrayOutput[3]);
        shortDescription.setText(arrayOutput[5]);
        value.setText(arrayOutput[7]);
        comment.setText(arrayOutput[2]);
        name.setText(arrayOutput[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        if (!(currentUser.getType().equals(UserType.LOCATIONEMPLOYEE))) {
            submit.setVisibility(View.GONE);
            time.setTag(time.getKeyListener());
            time.setKeyListener(null);

            location.setTag(location.getKeyListener());
            location.setKeyListener(null);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                final DatabaseReference ref = firebase.getReference("donations");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            if (snapshot.getKey().equals(keyUsed)) {
                                DonationDetail item = snapshot.getValue(DonationDetail.class);
                                String timeI = time.getText().toString();
                                String locationI = location.getText().toString();
                                String type = category.getSelectedItem().toString();
                                String fullDescriptionI = fullDescription.getText().toString();
                                String shortDescriptionI = shortDescription.getText().toString();
                                String valueI = value.getText().toString();
                                String commentI = comment.getText().toString();
                                String nameI = name.getText().toString();
                                item.setValues(new DonationDetail(timeI, locationI, fullDescriptionI, shortDescriptionI, valueI, type, commentI, nameI));
                                ref.child(keyUsed).setValue(item);
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent detail = new Intent(DonationDetailControl.this, LocationListViewPriv.class);
                                                startActivity(detail);
                                                finish();
                                            }
                                        },
                                        1000);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
        });

    }


}