package edu.gatech.cs2340_68b.donationtracker.View.donationViews;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340_68b.donationtracker.Models.Enum.Category;
import edu.gatech.cs2340_68b.donationtracker.Models.DonationDetail;
import edu.gatech.cs2340_68b.donationtracker.R;

public class DonationDetailControl_New extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_detail_new);

        final String locationName = (String) getIntent().getSerializableExtra("LOCATION");
        final DatabaseReference myRef = database.getReference("donations/");
        time = (EditText) findViewById(R.id.timeInput);
        fullDescription = (EditText) findViewById(R.id.fullDescriptionEdit);
        shortDescription = (EditText) findViewById(R.id.shortDescriptionEdit);
        value = (EditText) findViewById(R.id.valueEdit);
        comment = (EditText) findViewById(R.id.commentEdit);
        category = (Spinner)findViewById(R.id.categorySpinner);
        submit = (Button) findViewById(R.id.submit);
        name = (EditText) findViewById(R.id.nameEdit);


        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String timeI = time.getText().toString();
                final Category type = (Category) category.getSelectedItem();
                final String fullDescriptionI = fullDescription.getText().toString();
                final String shortDescriptionI = shortDescription.getText().toString();
                final String valueI = value.getText().toString();
                final String commentI = comment.getText().toString();
                final String nameI = name.getText().toString();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DonationDetail item = new DonationDetail(timeI, locationName, fullDescriptionI, shortDescriptionI, valueI, type.toString(), commentI, nameI);
                        DatabaseReference newRef = myRef.push();
                        newRef.setValue(item);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) { }
                });
            }
        });
    }
}
