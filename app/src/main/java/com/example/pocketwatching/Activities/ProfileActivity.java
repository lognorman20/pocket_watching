package com.example.pocketwatching.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.Clients.Ethplorer.EthplorerClient;
import com.example.pocketwatching.Models.Ethplorer.EthWallet;
import com.example.pocketwatching.Models.Ethplorer.Token;
import com.example.pocketwatching.Models.Ethplorer.TokenInfo;
import com.example.pocketwatching.Models.Wallet;
import com.example.pocketwatching.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private Button btnLogout;
    private TextView tvEthBalance;
    private TextView tvPortfolioValue;
    private TextView tvTopThreeTokens;
    private TextView tvCountTx;
    private TextView tvEthPrice;
    private TextView tvTotalTokens;

    private static List<EthWallet> userEthWallets;
    private List<Wallet> userWallets;
    private List<Token> valuableTokens;
    private List<Token> notValuableTokens;

    /************ Core functions ***********/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogout = findViewById(R.id.btnLogout);
        tvEthBalance = findViewById(R.id.tvEthBalance);
        tvPortfolioValue = findViewById(R.id.tvPortfolioValue);
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

    // gets EthWallet object from given address
    private synchronized void getEthWallet(String address) {
        Call<EthWallet> call = (Call<EthWallet>) EthplorerClient.getInstance().getEthplorerApi().getEthWallet(address);
        call.enqueue(new Callback<EthWallet>() {
            @Override
            public void onResponse(Call<EthWallet> call, Response<EthWallet> response) {
                userEthWallets.add(response.body());
                if (userEthWallets.size() == userWallets.size()) {
                    initValuableTokens();
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
        tvEthBalance.setText(getEthAmount().toString() + " ETH");
        tvCountTx.setText(getTxCount()  + " total transactions");
        tvEthPrice.setText("$" + getEthPrice());
        tvTotalTokens.setText( String.valueOf(getTotalTokens()));
        tvPortfolioValue.setText("$" + getPortfolioBalance().toString());
    }

    /***** Initialization functions *****/
    // init lists of valuable and not valuable tokens -- get total tokens here?
    private void initValuableTokens() {
        for (int i = 0; i < userEthWallets.size(); i++) {
            for (int j = 0; j < userEthWallets.get(i).getTokens().size(); j++) {
                Token token = userEthWallets.get(i).getTokens().get(j);
                TokenInfo tokenInfo = token.getTokenInfo();
                if (tokenInfo.getPrice().equals(false)) {
                    notValuableTokens.add(token);
                } else {
                    valuableTokens.add(token);
                }
            }
        }
    }

    /***** Getter functions *****/
    // gets the amount of eth in all wallets
    private Double getEthAmount() {
        Double tempBalance = 0.0;
        for (int i = 0; i < userEthWallets.size(); i++) {
            tempBalance += userEthWallets.get(i).getEth().getBalance();
        }
        return tempBalance;
    }

    // gets the total transaction count from a given wallet
    private int getTxCount() {
        int count = 0;
        for (int i = 0; i < userEthWallets.size(); i++) {
            count += userEthWallets.get(i).getCountTxs();
        }
        return count;
    }

    // gets current eth price
    private Double getEthPrice() {
        return userEthWallets.get(0).getEth().getPrice().getRate();
    }

    // gets total number of tokens that aren't ETH
    private int getTotalTokens() {
        return valuableTokens.size() + notValuableTokens.size();
    }

    // get total portfolio balance
    private Double getPortfolioBalance() {
        Double balance = getEthAmount();
        for (int i = 0; i < valuableTokens.size(); i++) {
            balance += getTokenBalance(valuableTokens.get(i));
        }
        return balance;
    }

    // get balance of a given token
    private Double getTokenAmount(Token token) {
        return token.getBalance();
    }

    // gets dollar value of token
    private Double getTokenBalance(Token token) {
        Double amount = getTokenAmount(token) * token.getTokenInfo().getDecimals();

//        Double balance = amount * price.getRate();
//        return balance;
        return amount;
    }
}