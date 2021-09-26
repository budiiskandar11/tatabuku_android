package com.tatabuku.app.model.fixed_asset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailAssetDepresiasi {

    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("nomor")
    @Expose
    private String nomor;
    @SerializedName("nilai_depresiasi")
    @Expose
    private Double nilaiDepresiasi;
    @SerializedName("urut")
    @Expose
    private String urut;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public Double getNilaiDepresiasi() {
        return nilaiDepresiasi;
    }

    public void setNilaiDepresiasi(Double nilaiDepresiasi) {
        this.nilaiDepresiasi = nilaiDepresiasi;
    }

    public String getUrut() {
        return urut;
    }

    public void setUrut(String urut) {
        this.urut = urut;
    }

}