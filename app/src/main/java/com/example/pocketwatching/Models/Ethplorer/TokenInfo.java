package com.example.pocketwatching.Models.Ethplorer;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenInfo {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("decimals")
    @Expose
    private String decimals;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("totalSupply")
    @Expose
    private String totalSupply;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("issuancesCount")
    @Expose
    private Integer issuancesCount;
    @SerializedName("price")
    @Expose
    private Object price;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDecimals() {
        return Double.parseDouble(decimals);
    }

    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public Object getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public static class DataStateDeserializer implements JsonDeserializer<TokenInfo> {
        @Override
        public TokenInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            TokenInfo tokenInfo = new Gson().fromJson(json, TokenInfo.class);
            JsonObject elem;
            try {
                elem = (JsonObject) json.getAsJsonObject().get("price");
            } catch (Exception e){
                elem = null;
            }

            if (elem != null) {
                Price price = new Gson().fromJson(json, Price.class);
                price.setRate(elem.get("rate").getAsDouble());
                price.setDiff(elem.get("diff").getAsDouble());
                price.setTs(elem.get("ts").getAsInt());
                price.setVolume24h(elem.get("volume24h").getAsDouble());
                price.setMarketCapUsd(elem.get("marketCapUsd").getAsDouble());
                price.setAvailableSupply(elem.get("availableSupply").getAsDouble());

                price.setCurrency(elem.get("currency").getAsString());

                tokenInfo.setPrice(price);
            }

            return tokenInfo;
        }
    }

}