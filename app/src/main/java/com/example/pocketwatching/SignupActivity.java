package com.example.pocketwatching;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etWallet;
    private Button btnSignup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // add case where user is already logged in?
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etWallet = findViewById(R.id.etWallet);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String wallet = etWallet.getText().toString();
                signupUser(username, password, wallet);
            }
        });
    }

    private void signupUser(String username, String password, String wallet) {
        ParseUser user = new ParseUser();

        user.setUsername(username);
        user.setPassword(password);
        user.put("walletAddress", wallet);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(SignupActivity.this, "Successfully signed up " + username, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "Failed to sign up " + username, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
