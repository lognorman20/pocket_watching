package com.example.pocketwatching.Models.Ethplorer.PortfolioValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Eth {
    @SerializedName("price")
    @Expose
    private Price price;
    @SerializedName("amount")
    @Expose
    private Double amount;
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
        return amount;
    }

    public void setAmount(Double balance) {
        this.amount = amount;
    }

    public String getRawBalance() {
        return rawAmount;
    }

    public void setRawAmount(String rawBalance) {
        this.rawAmount = rawAmount;
    }
}
