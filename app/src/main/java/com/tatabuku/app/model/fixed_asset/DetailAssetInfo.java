package com.tatabuku.app.model.fixed_asset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailAssetInfo {

    @SerializedName("asset_category")
    @Expose
    private String assetCategory;
    @SerializedName("usia_depresiasi")
    @Expose
    private Double usiaDepresiasi;
    @SerializedName("tahun_pembelian")
    @Expose
    private String tahunPembelian;

    public String getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
    }

    public Double getUsiaDepresiasi() {
        return usiaDepresiasi;
    }

    public void setUsiaDepresiasi(Double usiaDepresiasi) {
        this.usiaDepresiasi = usiaDepresiasi;
    }

    public String getTahunPembelian() {
        return tahunPembelian;
    }

    public void setTahunPembelian(String tahunPembelian) {
        this.tahunPembelian = tahunPembelian;
    }

}

