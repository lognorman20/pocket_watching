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

    // ***** Token Comparators for sorting ***** \\
    public static class CompAmount implements Comparator<Token> {
        @Override
        public int compare(Token leftToken, Token rightToken) {
            return leftToken.getAmount().compareTo(rightToken.getAmount());
        }
    }

    public static class CompPctChange implements Comparator<Token> {
        @Override
        public int compare(Token leftToken, Token rightToken) {
            Price leftPrice = (Price) leftToken.getTokenInfo().getPrice();
            Price rightPrice = (Price) rightToken.getTokenInfo().getPrice();

            Double left = leftPrice.getDiff();
            Double right = rightPrice.getDiff();
            return left.compareTo(right);
        }
    }

    public static class CompMarketCap implements Comparator<Token> {
        @Override
        public int compare(Token leftToken, Token rightToken) {
            Price leftPrice = (Price) leftToken.getTokenInfo().getPrice();
            Price rightPrice = (Price) rightToken.getTokenInfo().getPrice();

            Double left = leftPrice.getMarketCapUsd();
            Double right = rightPrice.getMarketCapUsd();
            return left.compareTo(right);
        }
    }

    public static class CompMarketPrice implements Comparator<Token> {
        @Override
        public int compare(Token leftToken, Token rightToken) {
            Price leftPrice = (Price) leftToken.getTokenInfo().getPrice();
            Price rightPrice = (Price) rightToken.getTokenInfo().getPrice();

            Double left = leftPrice.getRate();
            Double right = rightPrice.getRate();
            return left.compareTo(right);
        }
    }

    public static class CompCircSupply implements Comparator<Token> {
        @Override
        public int compare(Token leftToken, Token rightToken) {
            Price leftPrice = (Price) leftToken.getTokenInfo().getPrice();
            Price rightPrice = (Price) rightToken.getTokenInfo().getPrice();

            Double left = leftPrice.getAvailableSupply();
            Double right = rightPrice.getAvailableSupply();
            return left.compareTo(right);
        }
    }

    public static class CompVolume implements Comparator<Token> {
        @Override
        public int compare(Token leftToken, Token rightToken) {
            Price leftPrice = (Price) leftToken.getTokenInfo().getPrice();
            Price rightPrice = (Price) rightToken.getTokenInfo().getPrice();

            Double left = leftPrice.getVolume24h();
            Double right = rightPrice.getVolume24h();
            return left.compareTo(right);
        }
    }

    public static class CompBalance implements Comparator<Token> {
        @Override
        public int compare(Token leftToken, Token rightToken) {
            Double left = leftToken.getTokenBalance();
            Double right = rightToken.getTokenBalance();
            return left.compareTo(right);
        }
    }
}