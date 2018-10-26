package edu.gatech.cs2340_68b.donationtracker.Controllers.Location;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Register.ResetPassword;
import edu.gatech.cs2340_68b.donationtracker.Models.Category;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.Models.Enum.UserType;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.DonationList;
import edu.gatech.cs2340_68b.donationtracker.View.DonationList_Own;
import edu.gatech.cs2340_68b.donationtracker.View.LocationListViewPriv;
import edu.gatech.cs2340_68b.donationtracker.View.Welcome;

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
    public static boolean finishedFlag = false;



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

            String timeI = time.getText().toString();
            String locationI = location.getText().toString();
            //                Category type = (Category) category.getSelectedItem();
            String type = (String) category.getSelectedItem().toString();
            String fullDescriptionI = fullDescription.getText().toString();
            String shortDescriptionI = shortDescription.getText().toString();
            String valueI = value.getText().toString();
            String commentI = comment.getText().toString();
            String nameI = name.getText().toString();

//            @Override
//            public void onClick(View v) {
//                final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
//                final DatabaseReference ref = firebase.getReference("donations/" + locationUsed);
//                ref.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        DonationDetail detail = dataSnapshot.getValue(DonationDetail.class);
//                        DonationDetail newDetail = new DonationDetail(timeI, locationI, fullDescriptionI, shortDescriptionI, valueI, type, commentI, nameI);
//                        detail.setValues(newDetail);
//                        ref.child(dataSnapshot.getKey()).setValue(detail);
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {}
//                });
//            }

                        @Override
            public void onClick(View v) {
                System.out.println("Ladadad: " + nameI);
                final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
                final DatabaseReference ref = firebase.getReference("donations/" + locationUsed);
//                ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                        DonationDetail detail = new DonationDetail();
//                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                            System.out.println(snapshot.getKey());
//                            System.out.println(keyUsed);
//                            if (snapshot.getKey().equals(keyUsed)) {
//                                System.out.println("HERE");
//                                DonationDetail detail = snapshot.getValue(DonationDetail.class);
//                                DonationDetail newDetail = new DonationDetail(timeI, locationI, fullDescriptionI, shortDescriptionI, valueI, type, commentI, nameI);
//                                System.out.println("line 159: " + newDetail.getName());
//                                detail.setTime(timeI);
//                                detail.setLocation(locationI);
//                                detail.setFullDescription(fullDescriptionI);
//                                detail.setShortDescription(shortDescriptionI);
//                                detail.setValue(valueI);
//                                detail.setValue(commentI);
//                                detail.setName(nameI);
//                                detail.setCategory(type);
//                                System.out.println(detail.getName());
//                                ref.child(snapshot.getKey()).setValue(detail);
////                                AlertDialog.Builder alert  = CustomDialog.errorDialog(DonationDetailControl.this,
////                                        "Congratulation", "You have successfully changed your donation detail.");
////                                alert.create().show();
////                                new android.os.Handler().postDelayed(
////                                        new Runnable() {
////                                            @Override
////                                            public void run() {
////                                                finish();
////                                            }
////                                        },
////                                        1500);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) { }
//                });
            }
        });

    }


}