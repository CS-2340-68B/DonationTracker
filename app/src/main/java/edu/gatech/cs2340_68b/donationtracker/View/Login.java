package edu.gatech.cs2340_68b.donationtracker.View;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Controllers.HttpUtils;
import edu.gatech.cs2340_68b.donationtracker.Controllers.Register.ForgetPassword;
import edu.gatech.cs2340_68b.donationtracker.Models.Response;
import edu.gatech.cs2340_68b.donationtracker.Models.User;
import edu.gatech.cs2340_68b.donationtracker.R;

import static edu.gatech.cs2340_68b.donationtracker.View.Welcome.gson;

/**
 * Controller for login to the app, check account in the database
 */
@SuppressWarnings({"AssignmentToStaticFieldFromInstanceMethod", "FeatureEnvy", "ChainedMethodCall"})
public class Login extends AppCompatActivity {

    private TextView username;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = findViewById(R.id.registerUsername);
        password = findViewById(R.id.registerPassword);
        Button cancel = findViewById(R.id.cancel);
        Button login = findViewById(R.id.login);
        TextView resetPassword = findViewById(R.id.forgetPassword);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Object txt = username.getText();
                String inputUsername = txt.toString();
                Object pass = password.getText();
                String inputPassword = pass.toString();

                RequestParams query =  new RequestParams();
                query.put("email_signin", inputUsername);
                query.put("password_signin", inputPassword);
                HttpUtils.postForm("/signin", query, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Response res = gson.fromJson(response.toString(), Response.class);
                        Log.e("Tag: ", res.status);
                        switch (res.status) {
                            case "noAccount": {
                                AlertDialog.Builder alert = CustomDialog.errorDialog(Login.this,
                                        "Oops", "Email does not exist.");
                                alert.create().show();
                                break;
                            }
                            case "wrongPassword": {
                                AlertDialog.Builder alert = CustomDialog.errorDialog(Login.this,
                                        "Oops", "Wrong password.");
                                alert.create().show();
                                break;
                            }
                            case "accountLock": {
                                AlertDialog.Builder alert = CustomDialog.errorDialog(Login.this,
                                        "Sorry", "Account is currently lock. " +
                                                "Please reset your password or check your email");
                                alert.create().show();
                                break;
                            }
                            default:
                                User user = gson.fromJson(
                                        gson.toJsonTree(res.data).getAsJsonObject(), User.class);
                                Welcome.currentUser = user;
                                Welcome.userKey = user.getUserKey();
                                Intent intent = new Intent(Login.this, MainPage.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                    }
                });
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
    }
}
