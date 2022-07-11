package com.example.pocketwatching.Apis.Moralis;

import com.example.pocketwatching.Models.Moralis.BlockBalance;
import com.example.pocketwatching.Models.Moralis.DateToBlock;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MoralisApi {
    String BASE_URL = "https://deep-index.moralis.io/api/v2/";

    @Headers({"accept: application/json", "X-API-Key: ylLNSQYuTOCEKXV7ojV4cejlG0QTaCfzERcFIPvQmVKQmP66lZOS0mdrPTMY5CNG"})
    @GET("dateToBlock?chain=eth")
    Call<DateToBlock> timeToBlock(@Query("date") String date);

    @Headers({"accept: application/json", "X-API-Key: ylLNSQYuTOCEKXV7ojV4cejlG0QTaCfzERcFIPvQmVKQmP66lZOS0mdrPTMY5CNG"})
    @GET("{address}/balance?chain=eth")
    Call<BlockBalance> getBlockBalance(@Query("address") String address, @Query("to_block") long block);
}
