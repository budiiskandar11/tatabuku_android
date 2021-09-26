package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardCustomerTotalData{

    @SerializedName("tahun_ini")
    @Expose
    private Double tahunIni = 0.0;
    @SerializedName("bulan_ini")
    @Expose
    private Double bulanIni = 0.0;
    @SerializedName("hari_ini")
    @Expose
    private Double hariIni = 0.0;
    @SerializedName("saldo_dp")
    @Expose
    private Double saldoDp = 0.0;
    @SerializedName("dp_masuk")
    @Expose
    private Double dpMasuk = 0.0;
    @SerializedName("dp_keluar")
    @Expose
    private Double dpKeluar = 0.0;

    public Double getTahunIni() {
        return tahunIni;
    }

    public void setTahunIni(Double tahunIni) {
        this.tahunIni = tahunIni;
    }

    public Double getBulanIni() {
        return bulanIni;
    }

    public void setBulanIni(Double bulanIni) {
        this.bulanIni = bulanIni;
    }

    public Double getHariIni() {
        return hariIni;
    }

    public void setHariIni(Double hariIni) {
        this.hariIni = hariIni;
    }

    public Double getSaldoDp() {
        return saldoDp;
    }

    public void setSaldoDp(Double saldoDp) {
        this.saldoDp = saldoDp;
    }

    public Double getDpMasuk() {
        return dpMasuk;
    }

    public void setDpMasuk(Double dpMasuk) {
        this.dpMasuk = dpMasuk;
    }

    public Double getDpKeluar() {
        return dpKeluar;
    }

    public void setDpKeluar(Double dpKeluar) {
        this.dpKeluar = dpKeluar;
    }

}