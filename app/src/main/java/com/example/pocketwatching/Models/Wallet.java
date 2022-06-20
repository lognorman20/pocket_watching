package com.example.pocketwatching.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Wallet")
public class Wallet extends ParseObject {
    public static final String KEY_OWNER = "owner";
    public static final String KEY_SYMBOL = "symbol";
    public static final String KEY_WALLET_ADDRESS = "walletAddress";

    public String getOwner() {
        return getString(KEY_OWNER);
    }

    public String getSymbol() {
        return getString(KEY_SYMBOL);
    }

    public String getWalletAddress() {
        return getString(KEY_WALLET_ADDRESS);
    }

    public void setOwner(ParseUser user) {
        put(KEY_OWNER, user);
    }

    public void setSymbol(String symbol) {
        put(KEY_SYMBOL, symbol);
    }

    public void setWalletAddress(String walletAddress) {
        put(KEY_WALLET_ADDRESS, walletAddress);
    }
}
