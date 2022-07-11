package com.example.pocketwatching.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.Models.Wallet;
import com.example.pocketwatching.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AddWalletActivity extends AppCompatActivity {
    private EditText etWallet;
    private Button btnAddWallet;
    private ImageButton btnClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallets);

        etWallet = findViewById(R.id.etWallet);
        btnAddWallet = findViewById(R.id.btnAddWallet);
        btnClose = findViewById(R.id.btnClose);

        btnAddWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wallet walletObject = new Wallet();

                walletObject.setOwner(ParseUser.getCurrentUser());
                walletObject.setSymbol("eth");
                walletObject.setWalletAddress(etWallet.getText().toString());

                walletObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Toast.makeText(AddWalletActivity.this, "Successfully added wallet", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddWalletActivity.this, "Failed to add wallet", Toast.LENGTH_SHORT).show();
                            walletObject.deleteInBackground();
                        }
                    }
                });
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goProfileActivity();
            }
        });
    }

    private void goProfileActivity() {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
        finish();
    }
}
