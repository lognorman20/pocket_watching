package com.example.pocketwatching.Models.Ethplorer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

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
    private Double volume24h;
    @SerializedName("volDiff1")
    @Expose
    private Double volDiff1;
    @SerializedName("volDiff7")
    @Expose
    private Double volDiff7;
    @SerializedName("volDiff30")
    @Expose
    private Double volDiff30;
    @SerializedName("diff30d")
    @Expose
    private Double diff30d;

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

    public Double getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(Double volume24h) {
        this.volume24h = volume24h;
    }

    public Double getVolDiff1() {
        return volDiff1;
    }

    public void setVolDiff1(Double volDiff1) {
        this.volDiff1 = volDiff1;
    }

    public Double getVolDiff7() {
        return volDiff7;
    }

    public void setVolDiff7(Double volDiff7) {
        this.volDiff7 = volDiff7;
    }

    public Double getVolDiff30() {
        return volDiff30;
    }

    public void setVolDiff30(Double volDiff30) {
        this.volDiff30 = volDiff30;
    }

    public Double getDiff30d() {
        return diff30d;
    }

    public void setDiff30d(Double diff30d) {
        this.diff30d = diff30d;
    }

}