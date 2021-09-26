package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardRekeningModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("saldo")
    @Expose
    private Double saldo;
    @SerializedName("masuk")
    @Expose
    private Double masuk;
    @SerializedName("keluar")
    @Expose
    private Double keluar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}