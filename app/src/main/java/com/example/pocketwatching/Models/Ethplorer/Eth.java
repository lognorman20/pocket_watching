package com.example.pocketwatching.Models.Ethplorer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Eth {
    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("balance")
    @Expose
    private Double balance;
    @SerializedName("rawAmount")
    @Expose
    private String rawAmount;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Double getAmount() {
        return balance;
    }

    public void setAmount(Double balance) {
        this.balance = balance;
    }

    public String getRawBalance() {
        return rawAmount;
    }

    public void setRawAmount(String rawBalance) {
        this.rawAmount = rawAmount;
    }
}
