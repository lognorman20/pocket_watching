package com.example.pocketwatching.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Price;
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
        private TextView tvName;
        private TextView tvSymbol;
        private TextView tvAmountHeld;
        private TextView tvPctChange;
        private TextView tvCirculatingSupply;
        private TextView tvVolume;
        private TextView tvBalance;
        private TextView tvMarketCap;
        private TextView tvMarketPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvSymbol = itemView.findViewById(R.id.tvSymbol);
            tvAmountHeld = itemView.findViewById(R.id.tvAmountHeld);
            tvPctChange = itemView.findViewById(R.id.tvPctChange);
            tvCirculatingSupply = itemView.findViewById(R.id.tvCirculatingSupply);
            tvVolume = itemView.findViewById(R.id.tvVolume);
            tvBalance = itemView.findViewById(R.id.tvBalance);
            tvMarketCap = itemView.findViewById(R.id.tvMarketCap);
            tvMarketPrice = itemView.findViewById(R.id.tvMarketPrice);
        }

        public void bind(Token token) {
            Price price = (Price) token.getTokenInfo().getPrice();

            String name = token.getTokenInfo().getName();
            String symbol = token.getTokenInfo().getSymbol();
            String amountHeld = getString(token.getAmount());
            String pctChange = String.format("%,.2f", price.getDiff());
            String circulatingSupply = getString(price.getAvailableSupply());
            String volume = getString(price.getVolume24h());
            String balance = getString(token.getTokenBalance());
            String marketCap = getString(price.getMarketCapUsd());
            String marketPrice = String.format("%,.6f", price.getRate());

            tvName.setText(name);
            tvSymbol.setText(symbol);
            tvAmountHeld.setText(amountHeld + " " + symbol);
            tvPctChange.setText(pctChange + "%");
            tvCirculatingSupply.setText(circulatingSupply);
            tvVolume.setText("$" + volume);
            tvBalance.setText("$" + balance);
            tvMarketCap.setText("$" + marketCap);
            tvMarketPrice.setText("$" + marketPrice);
        }
    }

    public static String getString(double input){

        Long number = (long) input;

        if(number >= 1000000000000L){
            return String.format("%.2fT", number/ (float) 1000000000000L);
        }

        if(number >= 1000000000){
            return String.format("%.2fB", number/ 1000000000.0);
        }

        if(number >= 1000000){
            return String.format("%.2fM", number/ 1000000.0);
        }

        if(number >= 100000){
            return String.format("%.2fL", number/ 100000.0);
        }

        if(number >=1000){
            return String.format("%.2fK", number/ 1000.0);
        }

        return String.format("%,.2f", input);
    }
}
