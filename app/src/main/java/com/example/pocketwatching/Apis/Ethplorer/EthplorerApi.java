package com.example.pocketwatching.Apis.Ethplorer;

import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.EthWallet;
import com.example.pocketwatching.Models.Ethplorer.TxHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EthplorerApi {
    String BASE_URL = "https://api.ethplorer.io/";

    @GET("getAddressInfo/{address}?apiKey=freekey")
    Call<EthWallet> getEthWallet(@Path("address") String address);

    @GET("getAddressHistory/{address}?apiKey=freekey&type=transfer)")
    Call<TxHistory> getTxHistory(@Path("address") String address, @Query("limit") String limit);
}