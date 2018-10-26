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
import android.widget.TextView;

import edu.gatech.cs2340_68b.donationtracker.Models.Category;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.Models.Enum.UserType;
import edu.gatech.cs2340_68b.donationtracker.R;
import edu.gatech.cs2340_68b.donationtracker.View.DonationList;
import edu.gatech.cs2340_68b.donationtracker.View.DonationList_Own;
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
    private Spinner category;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C2331")));
        setContentView(R.layout.donation_detail);
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
                DonationDetail item = new DonationDetail();

                String timeI = time.getText().toString();
                String locationI = location.getText().toString();
                Category type = (Category) category.getSelectedItem();
                String fullDescriptionI = fullDescription.getText().toString();
                String shortDescriptionI = shortDescription.getText().toString();
                String valueI = value.getText().toString();
                String commentI = comment.getText().toString();

                item.setTime(timeI);
                item.setLocation(locationI);
                item.setFullDescription(fullDescriptionI);
                item.setCategory(type);
                item.setShortDescription(shortDescriptionI);
                item.setValue(valueI);
                item.setComment(commentI);

                Intent detail = new Intent(DonationDetailControl.this, DonationList_Own.class);
                startActivity(detail);
            }
        });

    }


}