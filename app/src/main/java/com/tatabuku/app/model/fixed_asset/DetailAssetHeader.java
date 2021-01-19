package com.tatabuku.app.model.fixed_asset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.util.StringHelper;

import java.io.Serializable;

public class DetailAssetHeader implements Serializable {

    @SerializedName("asset_id")
    @Expose
    private Integer assetId;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("asset_name")
    @Expose
    private String assetName;
    @SerializedName("tahun")
    @Expose
    private String tahun;
    @SerializedName("nilai_perolehan")
    @Expose
    private Double nilaiPerolehan;
    @SerializedName("depresiasi")
    @Expose
    private Double depresiasi;
    @SerializedName("nilai_buku")
    @Expose
    private Double nilaiBuku;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public Double getNilaiPerolehan() {
        return nilaiPerolehan;
    }

    public void setNilaiPerolehan(Double nilaiPerolehan) {
        this.nilaiPerolehan = nilaiPerolehan;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
