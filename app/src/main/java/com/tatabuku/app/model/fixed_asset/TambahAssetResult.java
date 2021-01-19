package com.tatabuku.app.model.fixed_asset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TambahAssetResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Asset_id")
    @Expose
    private Integer assetId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

}