package com.example.pocketwatching.Models.Ethplorer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class EthWallet extends JSONObject {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("ETH")
    @Expose
    private Eth eth;
    @SerializedName("countTxs")
    @Expose
    private Integer countTxs;
    @SerializedName("tokens")
    @Expose
    private List<Token> tokens = null;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Eth getEth() {
        return eth;
    }

    public void setEth(Eth eth) {
        this.eth = eth;
    }

    public Integer getCountTxs() {
        return countTxs;
    }

    public void setCountTxs(Integer countTxs) {
        this.countTxs = countTxs;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

}