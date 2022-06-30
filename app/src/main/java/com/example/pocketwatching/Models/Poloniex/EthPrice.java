package com.example.pocketwatching.Models.Poloniex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EthPrice {
    @SerializedName("date")
    @Expose
    private int date;
    @SerializedName("high")
    @Expose
    private float high;
    @SerializedName("low")
    @Expose
    private float low;
    @SerializedName("open")
    @Expose
    private float open;
    @SerializedName("close")
    @Expose
    private float close;
    @SerializedName("volume")
    @Expose
    private float volume;
    @SerializedName("quoteVolume")
    @Expose
    private float quoteVolume;
    @SerializedName("weightedAverage")
    @Expose
    private float weightedAverage;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(float quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public float getWeightedAverage() {
        return weightedAverage;
    }

    public void setWeightedAverage(float weightedAverage) {
        this.weightedAverage = weightedAverage;
    }
}
