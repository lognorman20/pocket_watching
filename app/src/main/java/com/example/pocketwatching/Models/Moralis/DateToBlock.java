package com.example.pocketwatching.Models.Moralis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateToBlock {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("block")
    @Expose
    private int block;
    @SerializedName("timestamp")
    @Expose
    private int timestamp;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
