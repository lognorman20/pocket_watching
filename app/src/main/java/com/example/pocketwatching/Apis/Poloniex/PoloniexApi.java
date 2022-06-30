package com.example.pocketwatching.Apis.Poloniex;

import com.example.pocketwatching.Models.Moralis.DateToBlock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PoloniexApi {
    String BASE_URL = "https://poloniex.com/public/";

    @GET("command=returnChartData&currencyPair=DAI_ETH&start={start}&end={end}&period=86400")
    Call<List<DateToBlock>> getEthPrices(@Query("start") String start, @Query("end") String end);
}