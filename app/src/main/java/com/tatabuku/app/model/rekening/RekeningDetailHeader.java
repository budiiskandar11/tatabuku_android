package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RekeningDetailHeader {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("saldo")
    @Expose
    private Double saldo;
    @SerializedName("masuk")
    @Expose
    private Double masuk;
    @SerializedName("keluar")
    @Expose
    private Double keluar;
    @SerializedName("tanggal_update")
    @Expose
    private String tanggalUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getMasuk() {
        return masuk;
    }

    public void setMasuk(Double masuk) {
        this.masuk = masuk;
    }

    public Double getKeluar() {
        return keluar;
    }

    public void setKeluar(Double keluar) {
        this.keluar = keluar;
    }

    public String getTanggalUpdate() {
        return tanggalUpdate;
    }

    public void setTanggalUpdate(String tanggalUpdate) {
        this.tanggalUpdate = tanggalUpdate;
    }

}