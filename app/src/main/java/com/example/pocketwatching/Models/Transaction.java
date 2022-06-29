package com.example.pocketwatching.Models;

import android.util.Log;

import com.parse.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// might be obsolete, txHistory handles these issues
public class Transaction {
    public String timestamp;
    public String from;
    public String to;
    public Double value;
    public Double usdPrice;
    public Double usdValue;
    public boolean success;

    public static final int SECOND_MILLIS = 1000;
    public static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    public static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    public static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static Transaction fromJson(TxHistory history) throws JSONException {
        Transaction tx = new Transaction();
        tx.from = history.getFrom();
        tx.success = history.isSuccess();
        tx.to = history.getTo();
        tx.timestamp = toDate(history.getTimestamp());
        tx.usdPrice = Double.valueOf(history.getUsdPrice());
        tx.usdValue = Double.valueOf(history.getUsdValue());
        tx.value = Double.valueOf(history.getValue());
        return tx;
    }

    public static List<Transaction> fromTxHistoryList(List<TxHistory> txHistory) throws JSONException {
        List<Transaction> txs = new ArrayList<>();
        for (int i = 0; i < txHistory.size(); i++) {
            Transaction tx = fromJson(txHistory.get(i));
            txs.add(tx);
        }
        return txs;
    }

    public static String toDate(long unixTime) {
        String format = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(format, Locale.ENGLISH);
        sf.setLenient(true);

        long time = unixTime;
        long now = System.currentTimeMillis();

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " m";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " h";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " d";
        }
    }
}
