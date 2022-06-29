package com.example.pocketwatching.Clients.Ethplorer;

import com.example.pocketwatching.Models.Ethplorer.PortfolioValues.EthWallet;
import com.example.pocketwatching.Models.TxHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EthplorerApi {
    String BASE_URL = "https://api.ethplorer.io/";

    @GET("getAddressInfo/{address}?apiKey=freekey")
    Call<EthWallet> getEthWallet(@Path("address") String address);

    @GET("getAddressTransactions/{address}?apiKey=freekey")
    Call<List<TxHistory>> getTxHistory(@Path("address") String address);
}