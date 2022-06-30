package com.example.pocketwatching.Models.Ethplorer.PortfolioValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("tokenInfo")
    @Expose
    private TokenInfo tokenInfo;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("totalIn")
    @Expose
    private Integer totalIn;
    @SerializedName("totalOut")
    @Expose
    private Integer totalOut;
    @SerializedName("rawBalance")
    @Expose
    private String rawBalance;

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(Integer totalIn) {
        this.totalIn = totalIn;
    }

    public Integer getTotalOut() {
        return totalOut;
    }

    public void setTotalOut(Integer totalOut) {
        this.totalOut = totalOut;
    }

    public String getRawAmount() {
        return rawBalance;
    }

    public void setRawAmount(String rawBalance) {
        this.rawBalance = rawBalance;
    }

    // gets token balance in $
    public Double getTokenBalance() {
        Double amount = getAmount() / (Math.pow(10, getTokenInfo().getDecimals()));
        Price price = (Price) getTokenInfo().getPrice(); // problem line
        Double balance = amount * price.getRate();
        return balance;
    }
}