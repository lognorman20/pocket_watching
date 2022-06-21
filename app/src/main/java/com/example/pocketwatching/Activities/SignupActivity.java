package com.example.pocketwatching.Activities;

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

public class SignupActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etWallet;
    private EditText etEmail;
    private Button btnSignup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etWallet = findViewById(R.id.etWallet);
        etEmail = findViewById(R.id.etEmail);
        btnSignup = findViewById(R.id.btnLoginLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String wallet = etWallet.getText().toString();
                String email = etEmail.getText().toString();
                signupUser(username, password, wallet, email);
            }
        });
    }

    private void signupUser(String username, String password, String wallet, String email) {
        // adding information to user table
        ParseUser user = new ParseUser();
        Wallet walletObject = new Wallet();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // adding information to wallet table
                    walletObject.setOwner(user);
                    walletObject.setWalletAddress(wallet);
                    walletObject.setSymbol("eth");
                    walletObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                Toast.makeText(SignupActivity.this, "Successfully signed up " + username, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignupActivity.this, "Failed to save wallet for " + username, Toast.LENGTH_SHORT).show();
                                Log.e("debugging", "Failed to save wallet for " + username + ". " + e);
                                user.deleteInBackground();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignupActivity.this, "Failed to sign up " + username, Toast.LENGTH_SHORT).show();
                    Log.e("debugging", "Could not sign up user " + username + ". " + e);
                }
            }
        });
    }
}
