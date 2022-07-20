package com.example.pocketwatching.Models.Ethplorer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price extends Object {

    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("diff")
    @Expose
    private Double diff;
    @SerializedName("ts")
    @Expose
    private Integer ts;
    @SerializedName("marketCapUsd")
    @Expose
    private Double marketCapUsd;
    @SerializedName("availableSupply")
    @Expose
    private Double availableSupply;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("volume24h")
    @Expose
    private Double volume24h;

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getVolume24h() {return volume24h;}

    public void setVolume24h(Double volume) {this.volume24h = volume;}

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
}