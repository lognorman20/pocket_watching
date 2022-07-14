package com.example.pocketwatching.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Token;
import com.example.pocketwatching.Models.Ethplorer.Transaction;
import com.example.pocketwatching.R;

import java.util.List;

public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.ViewHolder> {
    private Context context;
    private List<Token> tokens;

    public TokenAdapter(Context context, List<Token> inputTokens) {
        this.context = context;
        this.tokens = inputTokens;
    }

    @NonNull
    @Override
    public TokenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_token, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TokenAdapter.ViewHolder holder, int position) {
        Token token = tokens.get(position);
        holder.bind(token);
    }

    @Override
    public int getItemCount() {
        return tokens.size();
    }

    public void clear() {
        tokens.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Token> list) {
        tokens.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Token token) {
        }
    }
}
