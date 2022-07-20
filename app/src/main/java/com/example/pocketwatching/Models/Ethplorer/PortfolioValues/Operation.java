package com.example.pocketwatching.Models.Ethplorer.PortfolioValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Operation {
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;
    @SerializedName("transactionHash")
    @Expose
    private String transactionHash;
    @SerializedName("tokenInfo")
    @Expose
    private TokenInfo tokenInfo;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("priority")
    @Expose
    private int priority;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        Double amount = (Double) (value / (long) (Math.pow(10.0, getTokenInfo().getDecimals())));
        return amount;
    }

    public Double getUsdValue() {
        Double amount = getAmount();

        Price price = (Price) getTokenInfo().getPrice();
        Double marketPrice = price.getRate();

        return amount * marketPrice;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
