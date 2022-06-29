package com.example.pocketwatching.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketwatching.Models.Ethplorer.Transaction;
import com.example.pocketwatching.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    Context context;
    List<Transaction> txs;

    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.txs = transactions;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        Transaction tx = txs.get(position);
        holder.bind(tx);
    }

    @Override
    public int getItemCount() {
        return txs.size();
    }

    public void clear() {
        txs.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Transaction> list) {
        txs.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTo;
        TextView tvFrom;
        TextView tvTimestamp;
        TextView tvValue;
        TextView tvUsdPrice;
        TextView tvUsdValue;
        ImageView ivArrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTo = itemView.findViewById(R.id.tvTo);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvValue = itemView.findViewById(R.id.tvValue);
            tvUsdPrice = itemView.findViewById(R.id.tvUsdPrice);
            tvUsdValue = itemView.findViewById(R.id.tvUsdValue);
            ivArrow = itemView.findViewById(R.id.ivArrow);
        }

        public void bind(Transaction tx) {
            String value = String.format("%,.2f", tx.value);
            String usdPrice = "$" + String.format("%,.2f", tx.usdPrice);
            String usdValue = "$" + String.format("%,.2f", tx.usdValue);

            tvTo.setText(tx.to.substring(0,5) + "...");
            tvFrom.setText(tx.from.substring(0,5) + "...");
            tvTimestamp.setText(tx.timestamp);
            tvValue.setText(value);
            tvUsdPrice.setText(usdPrice);
            tvUsdValue.setText(usdValue);
            // add ability to change the arrow based on sending or receiving
        }
    }
}
