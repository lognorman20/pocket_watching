package com.example.pocketwatching.Clients.Ethplorer;

import com.example.pocketwatching.Models.Ethplorer.TokenInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EthplorerClient {
    private static EthplorerClient instance = null;
    private EthplorerApi myEthplorerApi;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(TokenInfo.class, new TokenInfo.DataStateDeserializer())
            .create();

    private EthplorerClient() {
        String BASE_URL = "https://api.ethplorer.io/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
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
