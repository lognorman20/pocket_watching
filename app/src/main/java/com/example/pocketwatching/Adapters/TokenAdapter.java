package com.example.pocketwatching.Adapters;

import android.content.Context;
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
            String amountHeld = String.format("%, .4f", token.getAmount());
            String pctChange = String.format("%,.2f", price.getDiff());
            String circulatingSupply = String.format("%, .2f", price.getAvailableSupply());
            String volume = String.format("%, .4f", price.getVolume24h());
            String balance = String.format("%, .2f", token.getTokenBalance());
            String marketCap = String.format("%, .2f", price.getMarketCapUsd());
            String marketPrice = String.format("%, .2f", price.getRate());

            Toast.makeText(context, price.getMarketCapUsd().toString() + " " + name, Toast.LENGTH_SHORT).show();

            tvName.setText(name);
            tvSymbol.setText(symbol);
            tvAmountHeld.setText(amountHeld + " " + symbol);
            tvPctChange.setText(pctChange + "%");
            tvCirculatingSupply.setText(circulatingSupply);
            tvVolume.setText(volume);
            tvBalance.setText("$" + balance);
            tvMarketCap.setText("$" + marketCap);
            tvMarketPrice.setText("$" + marketPrice);
        }
    }
}
