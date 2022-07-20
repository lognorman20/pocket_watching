package com.example.pocketwatching.Models.Ethplorer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

public class Token implements Serializable {

    @SerializedName("tokenInfo")
    @Expose
    private TokenInfo tokenInfo;
    @SerializedName("balance")
    @Expose
    private Double balance;

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public Double getAmount() {
        Double amount = (Double) (balance / (long) (Math.pow(10.0, getTokenInfo().getDecimals())));
        return amount;
    }

    public void setAmount(Double balance) {
        this.balance = balance;
    }

    // gets token balance in $
    public Double getTokenBalance() {
        Double amount = getAmount();
        Price price = (Price) getTokenInfo().getPrice(); // problem line
        Double balance = amount * price.getRate();
        return balance;
    }

    // Token Comparators for sorting
    public static class CompAmount implements Comparator<Token> {
        @Override
        public int compare(Token left, Token right) {
            return left.getAmount().compareTo(right.getAmount());
        }
    }
}