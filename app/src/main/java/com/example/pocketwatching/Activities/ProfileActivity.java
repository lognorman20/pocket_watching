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
import com.example.pocketwatching.Models.Ethplorer.Token;
import com.example.pocketwatching.Models.Ethplorer.TokenInfo;
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
    private TextView tvEthBalance;
    private TextView tvPortfolioBalance;
    private TextView tvTopThreeTokens;
    private TextView tvCountTx;
    private TextView tvEthPrice;
    private TextView tvTotalTokens;

    private static List<EthWallet> userEthWallets;
    private List<Wallet> userWallets;
    private List<TokenInfo> valuableTokens;
    private List<TokenInfo> notValuableTokens;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogout = findViewById(R.id.btnLogout);
        tvEthBalance = findViewById(R.id.tvEthBalance);
        tvPortfolioBalance = findViewById(R.id.tvPortfolioBalance);
        tvTopThreeTokens = findViewById(R.id.tvTopThreeTokens);
        tvTotalTokens = findViewById(R.id.tvTotalTokens);
        tvCountTx = findViewById(R.id.tvCountTx);
        tvEthPrice = findViewById(R.id.tvEthPrice);

        userEthWallets = new ArrayList<>();
        valuableTokens = new ArrayList<>();
        notValuableTokens = new ArrayList<>();

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

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                goMainActivity();
            }
        });
    }

    private synchronized void getEthWallet(String address) {
        Call<EthWallet> call = (Call<EthWallet>) EthplorerClient.getInstance().getEthplorerApi().getEthWallet(address);
        call.enqueue(new Callback<EthWallet>() {
            @Override
            public void onResponse(Call<EthWallet> call, Response<EthWallet> response) {
                userEthWallets.add(response.body());
                if (userEthWallets.size() == userWallets.size()) {
                    initValuableTokens();
                    Log.i("valuable tokens", String.valueOf(valuableTokens.size() + "..." + notValuableTokens.size()));
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

    /************ Helper functions ***********/

    /***** General helper functions *****/
    // takes the user to the main activity
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // this makes sure the Back button won't work
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // same as above
        startActivity(i);
        finish();
    }

    // binds values onto the display
    private void populateProfile() {
        tvEthBalance.setText(getEthBalance());
        tvCountTx.setText(getTxCount());
        tvEthPrice.setText(getEthPrice());
        tvTotalTokens.setText(getTotalTokens());
    }

    /***** Initialization functions *****/
    // init lists of valuable and not valuable tokens -- get total tokens here?
    private void initValuableTokens() {
        int count = 0;
        for (int i = 0; i < userEthWallets.size(); i++) {
            for (int j = 0; j < userEthWallets.get(i).getTokens().size(); j++) {
                Token token = userEthWallets.get(i).getTokens().get(j);
                TokenInfo tokenInfo = token.getTokenInfo();
                if (tokenInfo.getPrice().equals(false)) {
                    notValuableTokens.add(tokenInfo);
                } else {
                    valuableTokens.add(tokenInfo);
                }
            }
        }
    }

    /***** Getter functions *****/
    // gets the amount of eth in all wallets
    private String getEthBalance() {
        Double tempBalance = 0.0;
        for (int i = 0; i < userEthWallets.size(); i++) {
            tempBalance += userEthWallets.get(i).getEth().getBalance();
        }
        return tempBalance + " ETH";
    }

    // gets the total transaction count from a given wallet
    private static String getTxCount() {
        int count = 0;
        for (int i = 0; i < userEthWallets.size(); i++) {
            count += userEthWallets.get(i).getCountTxs();
        }
        String output = String.valueOf(count);
        return output + " total transactions";
    }

    // gets current eth price
    private String getEthPrice() {
        return "$" + userEthWallets.get(0).getEth().getPrice().getRate().toString();
    }

    // gets total number of tokens that aren't ETH
    private int getTotalTokens() {
        return valuableTokens.size() + notValuableTokens.size();
    }
}
