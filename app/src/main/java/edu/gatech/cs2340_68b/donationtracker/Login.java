package edu.gatech.cs2340_68b.donationtracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private Button login;
    private Button cancel;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (TextView)findViewById(R.id.username);
        password = (TextView)findViewById(R.id.password);
        cancel = (Button)findViewById(R.id.cancel);
        login = (Button)findViewById(R.id.login);
        ConstraintLayout current = (ConstraintLayout) findViewById(R.id.layout);
        current.setBackgroundColor(Color.WHITE);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference();
                myRef.setValue("Tuan");
                Intent intent = new Intent(Login.this, Welcome.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void verify(String username, String password) {
        if (username.equals("tuan") && password.equals("1234")) {
            Intent intent = new Intent(Login.this, MainPage.class);
            startActivity(intent);
        } else {

        }
    }
}
