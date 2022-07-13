package com.example.pocketwatching.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.Models.Wallet;
import com.example.pocketwatching.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.Locale;

public class SignupActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private Button btnSignup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnSignup = findViewById(R.id.btnLoginLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                signupUser(username, password, email);
            }
        });
    }

    private void signupUser(String username, String password, String email) {
        // adding information to user table
        // refactor such that the transaction history does not show, only visualize the bare minimum
        // if the user has a certain amount of wallets, do those parallel, fetch the first few in
        // parallel, add a load more button, reduce the number of calls necessary for each wallet
        ParseUser user = new ParseUser();
        // add bottle neck to the number of wallets
        user.setUsername(username.toLowerCase(Locale.ROOT));
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignupActivity.this, "Successfully signed up " + username, Toast.LENGTH_SHORT).show();
                    goAddWalletActivity();
                } else {
                    user.deleteInBackground();
                    Toast.makeText(SignupActivity.this, "Failed to sign up user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goAddWalletActivity() {
        Intent i = new Intent(this, AddWalletActivity.class);
        startActivity(i);
        finish();
    }
}
