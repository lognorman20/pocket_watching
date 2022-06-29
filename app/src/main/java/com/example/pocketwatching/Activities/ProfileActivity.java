package com.example.pocketwatching.Activities;

import static com.example.pocketwatching.Models.Transaction.fromTxHistoryList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketwatching.Adapters.TransactionAdapter;
import com.example.pocketwatching.Clients.Ethplorer.EthplorerClient;
import com.example.pocketwatching.Etc.TokenAmountComparator;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.EthWallet;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Token;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.TokenInfo;
import com.example.pocketwatching.Models.Transaction;
import com.example.pocketwatching.Models.TxHistory;
import com.example.pocketwatching.Models.Wallet;
import com.example.pocketwatching.R;
import com.google.common.collect.MinMaxPriorityQueue;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private TextView tvWelcome;

    // text views that don't change
    private TextView portfolioInformation;
    private TextView portfolioBalance;
    private TextView totalTokens;
    private TextView ethAmount;
    private TextView numTx;
    private TextView topThreeTokens;
    private TextView ethPrice;
    private TextView transactionHistory;


    private static List<EthWallet> userEthWallets;
    private List<Wallet> userWallets;
    private List<Token> valuableTokens;
    private List<Token> notValuableTokens;
    private ArrayList<Token> topTokensByAmount;

    private RecyclerView rvTransactions;
    private TransactionAdapter adapter;
    private List<Transaction> txs;

    private ProgressBar pbApi;

    public ProfileActivity() {}

    /**************************************************/
    /***************** Core Functions *****************/
    /**************************************************/
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
        tvWelcome = findViewById(R.id.tvWelcome);

        portfolioInformation = findViewById(R.id.portfolioInformation);
        portfolioBalance = findViewById(R.id.portfolioBalance);
        totalTokens = findViewById(R.id.totalTokens);
        ethAmount = findViewById(R.id.ethAmount);
        numTx = findViewById(R.id.numTx);
        topThreeTokens = findViewById(R.id.topThreeTokens);
        ethPrice = findViewById(R.id.ethPrice);
        transactionHistory = findViewById(R.id.transactionHistory);

        pbApi = findViewById(R.id.pbApi);
        pbApi.setVisibility(View.INVISIBLE);

        rvTransactions = findViewById(R.id.rvTransactions);
        txs = new ArrayList<>();
        adapter = new TransactionAdapter(this, txs);

        userEthWallets = new ArrayList<>();
        valuableTokens = new ArrayList<>();
        notValuableTokens = new ArrayList<>();
        topTokensByAmount = new ArrayList<>();

        startLoading();

        tvWelcome.setText(ParseUser.getCurrentUser().getUsername() + "'s Portfolio");

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
                        try {
                            Thread.sleep(2000); // optimize with observables?
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        getTxHistory(walletAddress);
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to get user wallets", Toast.LENGTH_SHORT).show();
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

        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setAdapter(adapter);
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
                    addPortfolioData();
                }
            }

            @Override
            public void onFailure(Call<EthWallet> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failed to get user wallet. " + t, Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private synchronized void getTxHistory(String address) {
        Call<List<TxHistory>> call = (Call<List<TxHistory>>) EthplorerClient.getInstance().getEthplorerApi().getTxHistory(address);
        call.enqueue(new Callback<List<TxHistory>>() {
            @Override
            public void onResponse(Call<List<TxHistory>> call, Response<List<TxHistory>> response) {
                try {
                    List<TxHistory> txHistory = response.body();
                    List<Transaction> cheese = fromTxHistoryList(txHistory);
                    txs.addAll(cheese);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(ProfileActivity.this, "Failed to add txHistory to txs", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<TxHistory>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failed to get txHistory", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**************************************************/
    /**************** Helper Functions ****************/
    /**************************************************/

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
    private void addPortfolioData() {
        String portfolioValue = "$" + String.format("%,.2f", getPortfolioBalance());
        String ethBalance = String.format("%,.2f", getTotalEthAmount()) + " ETH";
        String countTx = String.format("%,d", getTxCount()) + " total transactions";
        String ethPrice = "$" + String.format("%,.2f", getEthPrice());
        String totalTokens = String.format("%,d", getTotalTokens());
        String topThreeTokensText = String.valueOf(getTopThreeTokensByAmount());

        stopLoading();
        tvPortfolioValue.setText(portfolioValue);
        tvEthBalance.setText(ethBalance);
        tvCountTx.setText(countTx);
        tvEthPrice.setText(ethPrice);
        tvTotalTokens.setText(totalTokens);
        tvTopThreeTokens.setText(topThreeTokensText);
    }

    /***** Initialization functions *****/
    // init lists of valuable/not valuable tokens, top 3 tokens
    private void initValuableTokens() {
        Comparator<Token> comparator = new TokenAmountComparator();
        MinMaxPriorityQueue<Token> topThreeTokens = MinMaxPriorityQueue.orderedBy(comparator).create();

        for (int i = 0; i < userEthWallets.size(); i++) {
            for (int j = 0; j < userEthWallets.get(i).getTokens().size(); j++) {
                Token token = userEthWallets.get(i).getTokens().get(j);
                TokenInfo tokenInfo = token.getTokenInfo();
                if (tokenInfo.getPrice().equals(false)) {
                    notValuableTokens.add(token);
                } else {
                    topThreeTokens.add(token);
                    valuableTokens.add(token);
                }
            }
        }

        // gets the top three tokens by investment distribution, add balance based implementation??
        for (int i = 0; i < topThreeTokens.size(); i++) {
            topTokensByAmount.add(topThreeTokens.pollLast());
        }
    }

    // shows loading screen
    private void startLoading() {
        tvWelcome.setVisibility(View.INVISIBLE);
        btnLogout.setVisibility(View.INVISIBLE);
        rvTransactions.setVisibility(View.INVISIBLE);
        portfolioInformation.setVisibility(View.INVISIBLE);
        portfolioBalance.setVisibility(View.INVISIBLE);
        totalTokens.setVisibility(View.INVISIBLE);
        ethAmount.setVisibility(View.INVISIBLE);
        numTx.setVisibility(View.INVISIBLE);
        topThreeTokens.setVisibility(View.INVISIBLE);
        ethPrice.setVisibility(View.INVISIBLE);
        transactionHistory.setVisibility(View.INVISIBLE);

        pbApi.setVisibility(View.VISIBLE);
    }

    // hides loading screen
    private void stopLoading() {
        tvWelcome.setVisibility(View.VISIBLE);
        btnLogout.setVisibility(View.VISIBLE);
        rvTransactions.setVisibility(View.VISIBLE);
        portfolioInformation.setVisibility(View.VISIBLE);
        portfolioBalance.setVisibility(View.VISIBLE);
        totalTokens.setVisibility(View.VISIBLE);
        ethAmount.setVisibility(View.VISIBLE);
        numTx.setVisibility(View.VISIBLE);
        topThreeTokens.setVisibility(View.VISIBLE);
        ethPrice.setVisibility(View.VISIBLE);
        transactionHistory.setVisibility(View.VISIBLE);

        pbApi.setVisibility(View.INVISIBLE);
    }

    /***** Getter functions *****/
    // gets the amount of eth in all wallets
    private Double getTotalEthAmount() {
        Double tempAmount = 0.0;
        for (int i = 0; i < userEthWallets.size(); i++) {
            tempAmount += userEthWallets.get(i).getEth().getAmount();
        }
        return tempAmount;
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
        Double balance = 0.0;

        // getting eth balance from each wallet
        for (int i = 0; i < userEthWallets.size(); i++) {
            balance += userEthWallets.get(i).getEthBalance();
        }

        // getting other token balances from each wallet
        for (int i = 0; i < valuableTokens.size(); i++) {
            balance += valuableTokens.get(i).getTokenAmount();
        }
        return balance;
    }

    // gets list of top three tokens by amount
    private List<String> getTopThreeTokensByAmount() {
        List<String> output = new ArrayList<>();
        int i = 0;
        while (i < 3) {
            if (i < topTokensByAmount.size()) {
                output.add(topTokensByAmount.get(i).getTokenInfo().getSymbol());
            }
            i++;
        }
        return output;
    }
}