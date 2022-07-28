package com.example.pocketwatching.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketwatching.Models.Ethplorer.Price;
import com.example.pocketwatching.Models.Ethplorer.Token;
import com.example.pocketwatching.R;
import com.example.pocketwatching.Utils.Utils;

import java.util.List;

public class TopTokenAdapter extends RecyclerView.Adapter<TopTokenAdapter.ViewHolder> {
    private final int limit = 5;
    private Context context;
    private List<Token> tokens;

    public TopTokenAdapter(Context context, List<Token> tokens) {
        this.context = context;
        this.tokens = tokens;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_token, parent, false);
        return new TopTokenAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopTokenAdapter.ViewHolder holder, int position) {
        Token token = tokens.get(position);
        holder.bind(token);
    }

    @Override
    public int getItemCount() {
        if (tokens.size() > limit) {
            return limit;
        }
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
        private TextView tvItemSymbol;
        private TextView tvItemPctChange;
        private TextView tvItemBalance;
        private TextView tvItemAmountHeld;
        private TextView tvItemMarketPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemSymbol = itemView.findViewById(R.id.tvItemSymbol);
            tvItemPctChange = itemView.findViewById(R.id.tvItemPctChange);
            tvItemBalance = itemView.findViewById(R.id.tvItemBalance);
            tvItemAmountHeld = itemView.findViewById(R.id.tvItemAmountHeld);
            tvItemMarketPrice = itemView.findViewById(R.id.tvItemMarketPrice);
        }

        public void bind(Token token) {
            Price price = (Price) token.getTokenInfo().getPrice();

            String symbol = token.getTokenInfo().getSymbol();
            String pctChange = price.getDiff().toString() + "%";
            String balance = "Balance: $" + Utils.getString(token.getTokenBalance());
            String amountHeld = "Amount: " + Utils.getString(token.getAmount()) + " " + symbol;
            String marketPrice = "Market Price: $" + String.format("%,.2f", price.getRate());

            tvItemSymbol.setText(symbol);
            tvItemPctChange.setText(pctChange);
            tvItemBalance.setText(balance);
            tvItemAmountHeld.setText(amountHeld);
            tvItemMarketPrice.setText(marketPrice);

            if (price.getDiff() < 0) {
                tvItemPctChange.setTextColor(Color.parseColor("#a30716"));
            } else if (price.getDiff() > 0) {
                tvItemPctChange.setTextColor(Color.parseColor("#07a319"));
            } else {
                tvItemPctChange.setTextColor(Color.parseColor("#000000"));
            }
        }
    }
}