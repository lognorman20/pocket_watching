package com.example.pocketwatching.Clients;

import android.location.Address;

import com.example.pocketwatching.Models.Ethplorer.Eth;
import com.example.pocketwatching.Models.Ethplorer.EthWallet;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EthplorerApi {
    String BASE_URL = "https://api.ethplorer.io/";

    @GET("getAddressInfo/{address}?apiKey=freekey")
    Call<EthWallet> getEthWallet(@Path("address") String address);
}