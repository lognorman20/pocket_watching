package com.example.pocketwatching.Activities;

import static com.example.pocketwatching.Models.Ethplorer.Transaction.fromTxHistoryList;

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
import com.example.pocketwatching.Apis.Ethplorer.EthplorerClient;
import com.example.pocketwatching.Apis.Moralis.MoralisClient;
import com.example.pocketwatching.Apis.Poloniex.PoloniexClient;
import com.example.pocketwatching.Etc.TokenAmountComparator;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.EthWallet;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Token;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.TokenInfo;
import com.example.pocketwatching.Models.Ethplorer.Transaction;
import com.example.pocketwatching.Models.Ethplorer.TxHistory;
import com.example.pocketwatching.Models.Moralis.BlockBalance;
import com.example.pocketwatching.Models.Moralis.DateToBlock;
import com.example.pocketwatching.Models.Poloniex.EthPrice;
import com.example.pocketwatching.Models.Wallet;
import com.example.pocketwatching.R;
import com.google.common.collect.MinMaxPriorityQueue;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private static List<EthWallet> userEthWallets;

    // text views that change
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

    // variables for helper functions
    private List<Wallet> userWallets;
    private List<Token> valuableTokens;
    private List<Token> notValuableTokens;
    private List<Token> topTokensByAmount;
    private List<Integer> blockHeights;
    private List<Double> blockBalances;
    private List<Double> ethPrices;
    private List<Transaction> txs;

    // screen elements
    private RecyclerView rvTransactions;
    private TransactionAdapter adapter;

    // widgets and buttons
    private ProgressBar pbApi;
    private Button btnLogout;
    private Button btnViewHistoricalBalance;
    public ProfileActivity() {}

    /**************************************************/
    /***************** Core Functions *****************/
    /**************************************************/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        btnLogout = findViewById(R.id.btnLogout);

        rvTransactions = findViewById(R.id.rvTransactions);
        txs = new ArrayList<>();
        adapter = new TransactionAdapter(this, txs);

        userEthWallets = new ArrayList<>();
        valuableTokens = new ArrayList<>();
        notValuableTokens = new ArrayList<>();
        topTokensByAmount = new ArrayList<>();
        blockHeights = new ArrayList<>();
        blockBalances = new ArrayList<Double>(Collections.nCopies(7, -9.9));
        ethPrices = new ArrayList<Double>(Collections.nCopies(7, -9.9));

        startLoading();
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
                    getHistoricalBalance();
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to get user wallets", Toast.LENGTH_SHORT).show();
                    ParseUser.logOut();
                    finish();
                    goMainActivity();
                    return;
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
                Toast.makeText(ProfileActivity.this, "Failed to get user eth wallet.", Toast.LENGTH_SHORT).show();
                finish();
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

    private void getHistoricalBalance() {
        // get timestamps from each day of the past week
        List<String> times = new ArrayList<>();
        long currTime = System.currentTimeMillis() / 1000L;
        long tempTime = currTime;
        while (tempTime > (currTime - 604800)) {
            times.add(String.valueOf(tempTime));
            tempTime -= 86400;
        }
        Collections.sort(times);

        // get eth value for each day in the last week
        getEthPrices(times.get(0), times.get(6));
        // get block height at each timestamp in the past week
        // then for each wallet, get wallet balance at each block height in the past week in
        // getBlockHeight
        for (int i = 0; i < times.size(); i++) {
            Date date = new Date();
            date.setTime(Long.valueOf(times.get(i)) * 1000);
            getBlockHeight(times.get(i));
        }
        Log.i("unix timestamps", times.toString());
    }

    private void getEthPrices(String start, String end) {
        Call<List<EthPrice>> call = (Call<List<EthPrice>>) PoloniexClient.getInstance().getPoloniexApi().getEthPrices(start, end);
        call.enqueue(new Callback<List<EthPrice>>() {
            @Override
            public void onResponse(Call<List<EthPrice>> call, Response<List<EthPrice>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    ethPrices.set(i, Double.valueOf(response.body().get(i).getWeightedAverage()));
                }
            }

            @Override
            public void onFailure(Call<List<EthPrice>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failed to get historical eth prices", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBlockHeight(String timestamp) {
        Call<DateToBlock> call = (Call<DateToBlock>) MoralisClient.getInstance().getMoralisApi().timeToBlock(timestamp);
        call.enqueue(new Callback<DateToBlock>() {
            @Override
            public void onResponse(Call<DateToBlock> call, Response<DateToBlock> response) {
                blockHeights.add(response.body().getBlock());
                if (blockHeights.size() == 7) {
                    Collections.sort(blockHeights);
                    for (int i = 0; i < userWallets.size(); i++) {
                        for (int j = 0; j < 7; j++) {
                            getBlockBalance(userWallets.get(i).getWalletAddress(), blockHeights.get(j), j);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DateToBlock> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Could not get block from timestamp :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBlockBalance(String address, int blockHeight, int index) {
        Call<BlockBalance> call = (Call<BlockBalance>) MoralisClient.getInstance().getMoralisApi().getBlockBalance(address, blockHeight);
        call.enqueue(new Callback<BlockBalance>() {
            @Override
            public void onResponse(Call<BlockBalance> call, Response<BlockBalance> response) {
                blockBalances.set(index, Double.valueOf(response.body().getBalance()));
                if (index == 6) {
                    for (int i = 0; i < ethPrices.size(); i++) {
                        blockBalances.set(i, blockBalances.get(i) * ethPrices.get(i));
                    }
                    Log.i("block balances", blockBalances.toString());
                }
            }
            

            @Override
            public void onFailure(Call<BlockBalance> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failed to get balance at block " + String.valueOf(blockHeight), Toast.LENGTH_SHORT).show();
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

    private void goGraphActivity() {
        Intent i = new Intent(this, BalanceActivity.class);
        startActivity(i);
    }

    // binds values onto the display
    private void addPortfolioData() {
        String portfolioValue = "$" + String.format("%,.2f", getPortfolioBalance());
        String ethBalance = String.format("%,.2f", getTotalEthAmount()) + " ETH";
        String countTx = String.format("%,d", getTxCount()) + " total transactions";
        String ethPrice = "$" + String.format("%,.2f", getEthPrice());
        String totalTokens = String.format("%,d", getTotalTokens());
        String topThreeTokensText = String.valueOf(getTopThreeTokensByAmount());

        tvWelcome.setText(ParseUser.getCurrentUser().getUsername() + "'s Portfolio");
        tvPortfolioValue.setText(portfolioValue);
        tvEthBalance.setText(ethBalance);
        tvCountTx.setText(countTx);
        tvEthPrice.setText(ethPrice);
        tvTotalTokens.setText(totalTokens);
        tvTopThreeTokens.setText(topThreeTokensText);
        stopLoading();
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
            balance += valuableTokens.get(i).getTokenBalance();
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