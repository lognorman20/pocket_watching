package com.example.pocketwatching.Clients.Moralis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoralisClient {
    private static MoralisClient instance = null;
    private MoralisApi myMoralisApi;

    private MoralisClient() {
        String BASE_URL = "https://deep-index.moralis.io/api/v2/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myMoralisApi = retrofit.create(MoralisApi.class);
    }

    public static synchronized MoralisClient getInstance() {
        if (instance == null) {
            instance = new MoralisClient();
        }
        return instance;
    }

    public MoralisApi getMoralisApi() {
        return myMoralisApi;
    }
}
