package com.example.pocketwatching.Apis.Poloniex;

import com.example.pocketwatching.Apis.Moralis.MoralisApi;
import com.example.pocketwatching.Apis.Moralis.MoralisClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PoloniexClient {
    private static PoloniexClient instance = null;
    private PoloniexApi myPoloniexApi;

    private PoloniexClient() {
        String BASE_URL = "https://poloniex.com/public/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myPoloniexApi = retrofit.create(PoloniexApi.class);
    }

    public static synchronized PoloniexClient getInstance() {
        if (instance == null) {
            instance = new PoloniexClient();
        }
        return instance;
    }

    public PoloniexApi getPoloniexApi() {
        return myPoloniexApi;
    }
}
