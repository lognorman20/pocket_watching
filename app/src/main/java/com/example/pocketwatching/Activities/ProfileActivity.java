package com.example.pocketwatching.Activities;

import static java.util.Comparator.reverseOrder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pocketwatching.Clients.Ethplorer.EthplorerClient;
import com.example.pocketwatching.Etc.TokenBalanceComparator;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

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
    private PriorityQueue<Token> topThreeTokens;
    private Comparator<Token> comparator;

    /************ Core functions ***********/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogout = findViewById(R.id.btnLogout);
        tvEthBalance = findViewById(R.id.tvEthAmount);
        tvPortfolioValue = findViewById(R.id.tvPortfolioValue);
        tvTopThreeTokens = findViewById(R.id.tvTopThreeTokens);
        tvTotalTokens = findViewById(R.id.tvTotalTokens);
        tvCountTx = findViewById(R.id.tvCountTx);
        tvEthPrice = findViewById(R.id.tvEthPrice);

        userEthWallets = new ArrayList<>();
        valuableTokens = new ArrayList<>();
        notValuableTokens = new ArrayList<>();
//        comparator = new TokenBalanceComparator();
//        topThreeTokens = new PriorityQueue<Token>(Comparator.reverseOrder(), new Comparator<Token>() {
//            @Override
//            public int compare(Token one, Token two) {
//                if (one.getTokenBalance() < two.getTokenBalance()){
//                    return -1;
//                } else if (one.getTokenBalance() > two.getTokenBalance()) {
//                    return 1;
//                }
//                return 0;
//            }
//        });

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
                } else {
                    Log.e("debugging", "nah bro there was an error: " + e);

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
        Log.i("debugging", "init values on the screen...");
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
                Log.e("deserialize", t.toString());
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
        String portfolioValue = "$" + String.format("%,.2f", getPortfolioBalance());
        String ethBalance = String.format("%,.2f", getEthAmount()) + " ETH";
        String countTx = String.format("%,d", getTxCount()) + " total transactions";
        String ethPrice = "$" + String.format("%,.2f", getEthPrice());
        String totalTokens = String.format("%,d", getTotalTokens());

        tvPortfolioValue.setText(portfolioValue);
        tvEthBalance.setText(ethBalance);
        tvCountTx.setText(countTx);
        tvEthPrice.setText(ethPrice);
        tvTotalTokens.setText(totalTokens);
    }

    /***** Initialization functions *****/
    // init lists of valuable/not valuable tokens, top 3 tokens
    private void initValuableTokens() {
        for (int i = 0; i < userEthWallets.size(); i++) {
            for (int j = 0; j < userEthWallets.get(i).getTokens().size(); j++) {
                Token token = userEthWallets.get(i).getTokens().get(j);
                TokenInfo tokenInfo = token.getTokenInfo();
                if (tokenInfo.getPrice().equals(false)) {
                    notValuableTokens.add(token);
                } else {
                    Log.i("topthreetokens", String.valueOf(token.getTokenBalance()));
//                    topThreeTokens.add(token);
                    valuableTokens.add(token);
                }
            }
        }

//        Log.i("topthreetokens", "output:");
//        while (topThreeTokens.isEmpty() == false) {
//            Log.i("topthreetokens", topThreeTokens.remove().getTokenBalance().toString());
//        }
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
        Double balance = getEthBalance();
        for (int i = 0; i < valuableTokens.size(); i++) {
            balance += valuableTokens.get(i).getTokenBalance();
        }
        return balance;
    }

    // gets amount of a given token
    private Double getTokenAmount(Token token) {
        return token.getBalance();
    }

    // gets eth balance in $
    private Double getEthBalance() {
        return getEthAmount() * getEthPrice();
    }
}