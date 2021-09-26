package com.tatabuku.app.model.fixed_asset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListAssetHeader {

    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("pembelian")
    @Expose
    private Double pembelian;
    @SerializedName("depresiasi")
    @Expose
    private Double depresiasi;
    @SerializedName("nilai_buku")
    @Expose
    private Double nilaiBuku;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getPembelian() {
        return pembelian;
    }

    public void setPembelian(Double pembelian) {
        this.pembelian = pembelian;
    }

    public Double getDepresiasi() {
        return depresiasi;
    }

    public void setDepresiasi(Double depresiasi) {
        this.depresiasi = depresiasi;
    }

    public Double getNilaiBuku() {
        return nilaiBuku;
    }

    public void setNilaiBuku(Double nilaiBuku) {
        this.nilaiBuku = nilaiBuku;
    }
}