package com.example.pocketwatching.Models.Ethplorer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
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

    public String getRawBalance() {
        return rawBalance;
    }

    public void setRawBalance(String rawBalance) {
        this.rawBalance = rawBalance;
    }

    // gets token balance in $
    public Double getTokenBalance() {
        Double amount = getBalance() / (Math.pow(10, getTokenInfo().getDecimals()));
        Price price = (Price) getTokenInfo().getPrice(); // problem line
        Double balance = amount * price.getRate();
        return balance;
    }
}