package com.example.pocketwatching.Models.Poloniex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EthPrice {
    @SerializedName("date")
    @Expose
    private int date;
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
