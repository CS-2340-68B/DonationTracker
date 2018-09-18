package edu.gatech.cs2340_68b.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private Button login;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (TextView)findViewById(R.id.username);
        password = (TextView)findViewById(R.id.password);
        cancel = (Button)findViewById(R.id.cancel);
        login = (Button)findViewById(R.id.login);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
