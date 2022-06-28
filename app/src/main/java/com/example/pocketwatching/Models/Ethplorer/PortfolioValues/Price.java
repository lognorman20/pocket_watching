package com.example.pocketwatching.Models.Ethplorer.PortfolioValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price extends Object {

    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("diff")
    @Expose
    private Double diff;
    @SerializedName("diff7d")
    @Expose
    private Double diff7d;
    @SerializedName("ts")
    @Expose
    private Integer ts;
    @SerializedName("marketCapUsd")
    @Expose
    private Double marketCapUsd;
    @SerializedName("availableSupply")
    @Expose
    private Double availableSupply;
    @SerializedName("volume24h")
    @Expose
    private Double volDiff30;
    @SerializedName("diff30d")
    @Expose
    private Double diff30d;
    @SerializedName("currency")
    @Expose
    private String currency;

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getDiff() {
        return diff;
    }

    public void setDiff(Double diff) {
        this.diff = diff;
    }

    public Double getDiff7d() {
        return diff7d;
    }

    public void setDiff7d(Double diff7d) {
        this.diff7d = diff7d;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public Double getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(Double marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public Double getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(Double availableSupply) {
        this.availableSupply = availableSupply;
    }

    public Double getDiff30d() {
        return diff30d;
    }

    public void setDiff30d(Double diff30d) {
        this.diff30d = diff30d;
    }

}