package com.example.pocketwatching.Models.Ethplorer;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
        Date date = new Date();
        date.setTime(unixTime * 1000);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("M/dd/yy h:mm a");
        TimeZone tz = TimeZone.getDefault();
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(String.valueOf(tz)));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}