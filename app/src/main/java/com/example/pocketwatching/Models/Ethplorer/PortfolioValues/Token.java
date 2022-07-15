package com.example.pocketwatching.Models.Ethplorer.PortfolioValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Token implements Serializable {

    @SerializedName("tokenInfo")
    @Expose
    private TokenInfo tokenInfo;
    @SerializedName("balance")
    @Expose
    private Double balance;
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
        Double amount = (Double) (balance / (long) (Math.pow(10.0, getTokenInfo().getDecimals())));
        return amount;
    }

    public void setAmount(Double balance) {
        this.balance = balance;
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
        Double amount = getAmount();
        Price price = (Price) getTokenInfo().getPrice(); // problem line
        Double balance = amount * price.getRate();
        return balance;
    }
}