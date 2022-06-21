package com.example.pocketwatching.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.pocketwatching.R;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    private Button btnSignup;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignup = findViewById(R.id.btnLoginLogin);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginActivity();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignupActivity();
            }
        });
    }

    private void goSignupActivity() {
        if (ParseUser.getCurrentUser() != null){
            ParseUser.logOut();
        }
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

    private void goLoginActivity() {
        Intent i;
        if (ParseUser.getCurrentUser() != null) {
            Log.i("debugging", "User already logged in");
            i = new Intent(this, ProfileActivity.class);
        } else {
            Log.i("debugging", "User not logged in");
            i = new Intent(this, LoginActivity.class);
        }
        startActivity(i);
    }


}