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

public class Transaction {
    private String timestamp;
    private String from;
    private String to;
    private Double value;
    private Double usdPrice;
    private Double usdValue;
    private boolean success;

    public static Transaction fromJson(JSONObject jsonObject) throws JSONException {
        Transaction tx = new Transaction();
        tx.from = (String) jsonObject.get("from");
        tx.success = (boolean) jsonObject.get("success");
        tx.to = (String) jsonObject.get("to");
        tx.timestamp = toDate((String) jsonObject.get("timestamp"));
        tx.usdPrice = (Double) jsonObject.get("usdPrice");
        tx.usdValue = (Double) jsonObject.get("usdValue");
        tx.value = (Double) jsonObject.get("value");
        return tx;
    }

    public static List<Transaction> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Transaction> txs = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Transaction newTx = fromJson(jsonArray.getJSONObject(i));
            if (newTx != null) {
                txs.add(newTx);
            }
        }
        return txs;
    }

    public static String toDate(String unixTime) {
        Date date = new Date();
        date.setTime(Long.parseLong(unixTime) * 1000);
        return String.valueOf(date);
    }


}
