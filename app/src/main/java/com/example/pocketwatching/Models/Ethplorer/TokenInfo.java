package com.example.pocketwatching.Models.Ethplorer;

import android.util.Log;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
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
    @SerializedName("lastUpdated")
    @Expose
    private Integer lastUpdated;
    @SerializedName("issuancesCount")
    @Expose
    private Integer issuancesCount;
    @SerializedName("holdersCount")
    @Expose
    private Integer holdersCount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("ethTransfersCount")
    @Expose
    private Integer ethTransfersCount;
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("publicTags")
    @Expose
    private List<String> publicTags = null;

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

    public Integer getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Integer lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getIssuancesCount() {
        return issuancesCount;
    }

    public void setIssuancesCount(Integer issuancesCount) {
        this.issuancesCount = issuancesCount;
    }

    public Integer getHoldersCount() {
        return holdersCount;
    }

    public void setHoldersCount(Integer holdersCount) {
        this.holdersCount = holdersCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getEthTransfersCount() {
        return ethTransfersCount;
    }

    public void setEthTransfersCount(Integer ethTransfersCount) {
        this.ethTransfersCount = ethTransfersCount;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public List<String> getPublicTags() {
        return publicTags;
    }

    public void setPublicTags(List<String> publicTags) {
        this.publicTags = publicTags;
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
                price.setDiff7d(elem.get("diff7d").getAsDouble());
                price.setTs(elem.get("ts").getAsInt());
                price.setMarketCapUsd(elem.get("marketCapUsd").getAsDouble());
                price.setAvailableSupply(elem.get("availableSupply").getAsDouble());

                if (elem.has("diff30d")){
                    price.setDiff30d(elem.get("diff30d").getAsDouble());
                }

                price.setDiff7d(elem.get("diff7d").getAsDouble());
                price.setCurrency(elem.get("currency").getAsString());

                tokenInfo.setPrice(price);
            }

            return tokenInfo;
        }
    }

}