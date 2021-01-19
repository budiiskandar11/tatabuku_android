package com.tatabuku.app.model.fixed_asset;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardAssetHeader {

    @SerializedName("Total Asset Value")
    @Expose
    private Double totalAssetValue;
    @SerializedName("Jumlah Asset")
    @Expose
    private Integer jumlahAsset;

    public Double getTotalAssetValue() {
        return totalAssetValue;
    }

    public void setTotalAssetValue(Double totalAssetValue) {
        this.totalAssetValue = totalAssetValue;
    }

    public Integer getJumlahAsset() {
        return jumlahAsset;
    }

    public void setJumlahAsset(Integer jumlahAsset) {
        this.jumlahAsset = jumlahAsset;
    }
}
