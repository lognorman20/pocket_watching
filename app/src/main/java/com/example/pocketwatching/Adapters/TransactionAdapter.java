package com.example.pocketwatching.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketwatching.Models.Transaction;
import com.example.pocketwatching.R;

import org.json.JSONException;

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
        try {
            holder.bind(tx);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Transaction tx) {
        }
    }
}
