package com.example.pocketwatching.Models.Moralis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlockBalance {
    @SerializedName("balance")
    @Expose
    private String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
