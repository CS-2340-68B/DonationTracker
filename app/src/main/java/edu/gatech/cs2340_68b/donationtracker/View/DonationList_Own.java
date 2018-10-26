package edu.gatech.cs2340_68b.donationtracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Location.DonationDetailControl_New;
import edu.gatech.cs2340_68b.donationtracker.R;

public class DonationList_Own extends AppCompatActivity{

    private Button addButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_list_own);

        addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationList_Own.this, DonationDetailControl_New.class);
                startActivity(intent);
            }
        });
    }
}
