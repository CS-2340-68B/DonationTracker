package edu.gatech.cs2340_68b.donationtracker.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.FirebaseDatabase;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Login.Login;
import edu.gatech.cs2340_68b.donationtracker.R;

public class Welcome extends AppCompatActivity {

    private ImageButton imageButton;
    private Button loginButton;
    private Button registerButton;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
//        final ConstraintLayout current = (ConstraintLayout) findViewById(R.id.layout);
//        current.setBackgroundColor(Color.WHITE);
//        final ConstraintLayout current2 = (ConstraintLayout) findViewById(R.id.layout2);
//        current2.setVisibility(View.GONE);
//        current2.setBackgroundColor(Color.WHITE);


//        imageButton  = (ImageButton) findViewById(R.id.imageButton);
        loginButton = (Button) findViewById(R.id.LoginButton);
//        loginButton.setVisibility(View.GONE);
        registerButton = (Button) findViewById(R.id.RegisterButton);
//        registerButton.setVisibility(View.GONE);



//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageButton.animate().alpha(0);
//                current.setVisibility(View.GONE);
//                current2.setVisibility(View.VISIBLE);
//                loginButton.setVisibility(View.VISIBLE);
//                registerButton.setVisibility(View.VISIBLE);
//            }
//            });
//
//        current2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageButton.animate().alpha(1);
//                current.setVisibility(View.VISIBLE);
//                current2.setVisibility(View.GONE);
//                loginButton.setVisibility(View.GONE);
//                registerButton.setVisibility(View.GONE);
//            }
//        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Login.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
