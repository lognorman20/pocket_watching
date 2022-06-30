package com.example.pocketwatching.Clients.Moralis;

import com.example.pocketwatching.Models.Moralis.BlockBalance;
import com.example.pocketwatching.Models.Moralis.DateToBlock;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoralisApi {
    String BASE_URL = "https://deep-index.moralis.io/api/v2/";

    @Headers({"accept: application/json", "X-API-Key: ylLNSQYuTOCEKXV7ojV4cejlG0QTaCfzERcFIPvQmVKQmP66lZOS0mdrPTMY5CNG"})
    @GET("dateToBlock?chain=eth")
    Call<DateToBlock> getDateToBlock(@Query("date") String date);

    @GET("{address}/balance?chain=eth&to_block={block}")
    Call<BlockBalance> getBlockBalance(@Path("address") String address, @Path("block") int block);
}
