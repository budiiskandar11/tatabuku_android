package com.tatabuku.app.model.home;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomepageDataResult {

    @SerializedName("hutang_saya")
    @Expose
    private Double hutangSaya = 0.0;
    @SerializedName("hutang_pelanggan")
    @Expose
    private Double hutangPelanggan = 0.0;
    @SerializedName("total_pembelian")
    @Expose
    private Double totalPembelian = 0.0;
    @SerializedName("total_penjualan")
    @Expose
    private Double totalPenjualan = 0.0;
    @SerializedName("bank_data")
    @Expose
    private List<BankResult> bankData = null;

    public Double getHutangSaya() {
        return hutangSaya;
    }

    public void setHutangSaya(Double hutangSaya) {
        this.hutangSaya = hutangSaya;
    }

    public Double getHutangPelanggan() {
        return hutangPelanggan;
    }

    public void setHutangPelanggan(Double hutangPelanggan) {
        this.hutangPelanggan = hutangPelanggan;
    }

    public Double getTotalPembelian() {
        return totalPembelian;
    }

    public void setTotalPembelian(Double totalPembelian) {
        this.totalPembelian = totalPembelian;
    }

    public Double getTotalPenjualan() {
        return totalPenjualan;
    }

    public void setTotalPenjualan(Double totalPenjualan) {
        this.totalPenjualan = totalPenjualan;
    }

    public List<BankResult> getBankData() {
        return bankData;
    }

    public void setBankData(List<BankResult> bankData) {
        this.bankData = bankData;
    }

}