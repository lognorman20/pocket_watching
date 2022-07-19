package com.example.pocketwatching.Models.Ethplorer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.longrunning.Operation;

import org.json.JSONObject;

import java.util.List;

public class TxHistory extends JSONObject {
    @SerializedName("operations")
    @Expose
    private List<com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Operation> operations = null;

    public List<com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<com.example.pocketwatching.Models.Ethplorer.PortfolioValues.Operation> operations) {
        this.operations = operations;
    }
}