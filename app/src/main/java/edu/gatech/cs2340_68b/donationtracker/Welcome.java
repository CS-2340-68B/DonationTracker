package edu.gatech.cs2340_68b.donationtracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.google.firebase.database.DatabaseReference;

public class Welcome extends AppCompatActivity {

    private ImageButton imageButton;
    private Button loginButton;
    private Button registerButton;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        final ConstraintLayout current = (ConstraintLayout) findViewById(R.id.layout);
        current.setBackgroundColor(Color.WHITE);
        final ConstraintLayout current2 = (ConstraintLayout) findViewById(R.id.layout2);
        current2.setBackgroundColor(Color.WHITE);
        current2.setVisibility(View.GONE);


        imageButton  = (ImageButton) findViewById(R.id.imageButton);
        loginButton = (Button) findViewById(R.id.LoginButton);
        loginButton.setVisibility(View.GONE);
        registerButton = (Button) findViewById(R.id.RegisterButton);
        registerButton.setVisibility(View.GONE);
        imageView = (ImageView) findViewById(R.id.imageSmall);
        imageView.setVisibility(View.GONE);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.setVisibility(View.GONE);
                current.setVisibility(View.GONE);
                current2.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                registerButton.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);

            }
            });



    }
}
