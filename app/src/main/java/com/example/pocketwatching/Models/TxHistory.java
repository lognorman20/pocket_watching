package com.example.pocketwatching.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class TxHistory extends JSONObject {

    @SerializedName("timestamp")
    @Expose
    private int timestamp;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("value")
    @Expose
    private float value;
    @SerializedName("usdPrice")
    @Expose
    private float usdPrice;
    @SerializedName("usdValue")
    @Expose
    private float usdValue;
    @SerializedName("success")
    @Expose
    private boolean success;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getUsdPrice() {
        return usdPrice;
    }

    public void setUsdPrice(float usdPrice) {
        this.usdPrice = usdPrice;
    }

    public float getUsdValue() {
        return usdValue;
    }

    public void setUsdValue(float usdValue) {
        this.usdValue = usdValue;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}