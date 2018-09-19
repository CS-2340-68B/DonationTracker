package edu.gatech.cs2340_68b.donationtracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Welcome extends AppCompatActivity {


    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        ConstraintLayout current = (ConstraintLayout) findViewById(R.id.layout);
        current.setBackgroundColor(Color.WHITE);

        imageButton = (ImageButton)findViewById(R.id.imageButton);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
