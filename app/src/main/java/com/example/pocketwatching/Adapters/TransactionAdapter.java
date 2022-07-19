package com.example.pocketwatching.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketwatching.Fragments.ProfileFragment;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.EthWallet;
import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Operation;
import com.example.pocketwatching.R;
import com.example.pocketwatching.Utils.ClaimsXAxisValueFormatter;
import com.example.pocketwatching.Utils.Utils;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private Context context;
    private List<Operation> operations;
    private List<String> wallets;
    private String username;

    public TransactionAdapter(Context context, List<Operation> operations, List<EthWallet> inputWallets, String username) {
        this.context = context;
        this.operations = operations;
        this.username = username;

        List<String> walletAddresses = new ArrayList<>();
        for (int i = 0; i < inputWallets.size(); i++) {
            walletAddresses.add(inputWallets.get(i).getAddress());
        }

        this.wallets = walletAddresses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        Operation operation = operations.get(position);
        holder.bind(operation);
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

    public void clear() {
        operations.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Operation> list) {
        operations.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTxTime;
        private TextView tvTxHash;
        private TextView tvTx;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTxTime = itemView.findViewById(R.id.tvTxTime);
            tvTxHash = itemView.findViewById(R.id.tvTxHash);
            tvTx = itemView.findViewById(R.id.tvTx);
        }

        public void bind(Operation operation) {
            String time = toDate(operation.getTimestamp());
            String hash = "Tx Hash: " + operation.getTransactionHash().substring(0, 24) + "...";
            String symbol = operation.getTokenInfo().getSymbol();

            String tx;
            String amount = Utils.getString(operation.getAmount());
            String usdValue = "$" + Utils.getString(operation.getUsdValue());

            if (wallets.contains(operation.getTo())) {
                String from = operation.getFrom().substring(0, 18);
                tx = username + " received " + amount + " " + symbol + " (" + usdValue + ")" + " from " + from;
            } else {
                String to = operation.getTo().substring(0, 18) + "...";
                tx = username + " sent " + amount + " " + symbol + " (" + usdValue + ")" + " to " + to;
            }

            tvTxTime.setText(time);
            tvTxHash.setText(hash);
            tvTx.setText(tx);
        }
    }

    public static String toDate(long unixTime) {
        Date date = new Date();
        date.setTime(unixTime * 1000);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd hh:ss");
        TimeZone tz = TimeZone.getDefault();
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(String.valueOf(tz)));
        return sdf.format(date);
    }
}