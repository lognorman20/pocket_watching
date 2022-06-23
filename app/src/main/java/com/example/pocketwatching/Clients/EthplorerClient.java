package com.example.pocketwatching.Clients;

import com.example.pocketwatching.Models.Ethplorer.EthWallet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class EthplorerClient {
    private static EthplorerClient instance = null;
    private EthplorerApi myEthplorerApi;

    private EthplorerClient() {
        String BASE_URL = "https://api.ethplorer.io/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myEthplorerApi = retrofit.create(EthplorerApi.class);
    }

    public static synchronized EthplorerClient getInstance() {
        if (instance == null) {
            instance = new EthplorerClient();
        }
        return instance;
    }

    public EthplorerApi getEthplorerApi() {
        return myEthplorerApi;
    }
}
