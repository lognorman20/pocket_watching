package com.example.pocketwatching.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.Clients.EthplorerClient;
import com.example.pocketwatching.Models.Ethplorer.Eth;
import com.example.pocketwatching.Models.Ethplorer.EthWallet;
import com.example.pocketwatching.Models.Wallet;
import com.example.pocketwatching.R;
import com.google.gson.JsonObject;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private Button btnLogout;
    private TextView ethBalance;
    private TextView txCount;
    private TextView ethDiff;

    private List<EthWallet> userEthWallets;
    private List<Wallet> userWallets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogout = findViewById(R.id.btnLogout);
        ethBalance = findViewById(R.id.tvEthBalance);
        txCount = findViewById(R.id.tvCountTx);
        ethDiff = findViewById(R.id.tvEthDiff);

        userEthWallets = new ArrayList<>();

        ParseQuery<Wallet> query = ParseQuery.getQuery(Wallet.class);
        query.whereEqualTo("owner", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Wallet>() {
            @Override
            public void done(List<Wallet> objects, ParseException e) {
                if (e == null) {
                    userWallets = objects;
                    for (int i = 0; i < userWallets.size(); i++) {
                        String walletAddress = userWallets.get(i).getWalletAddress();
                        getEthWallet(walletAddress);
                    }
                }
            }
        });

        Log.i("debugging", String.valueOf(userEthWallets.size()));
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // this makes sure the Back button won't work
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // same as above
        startActivity(i);
        finish();
    }

    private synchronized void getEthWallet(String address) {
        Call<EthWallet> call = (Call<EthWallet>) EthplorerClient.getInstance().getEthplorerApi().getEthWallet(address);
        call.enqueue(new Callback<EthWallet>() {
            @Override
            public void onResponse(Call<EthWallet> call, Response<EthWallet> response) {
                userEthWallets.add(response.body());
                if (userEthWallets.size() == userWallets.size()) {
                    populateProfile();
                }
            }

            @Override
            public void onFailure(Call<EthWallet> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failed to get user wallet", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private void populateProfile() {
        ethBalance.setText(userEthWallets.get(0).getEth().getBalance().toString());
        ethDiff.setText(userEthWallets.get(0).getEth().getPrice().toString());
        txCount.setText(userEthWallets.get(0).getCountTxs());
    }
}
