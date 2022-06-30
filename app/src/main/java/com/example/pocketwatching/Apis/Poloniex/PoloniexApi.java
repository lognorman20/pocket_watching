package com.example.pocketwatching.Apis.Poloniex;

import com.example.pocketwatching.Models.Moralis.DateToBlock;
import com.example.pocketwatching.Models.Poloniex.EthPrice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PoloniexApi {
    String BASE_URL = "https://poloniex.com/";

    @GET("public?command=returnChartData&currencyPair=DAI_ETH&period=86400")
    Call<List<EthPrice>> getEthPrices(
            @Query("start") String start,
            @Query("end") String end);
}