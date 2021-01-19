package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardSupplierResult {

    @SerializedName("total_purchase_tahun")
    @Expose
    private Double totalPurchaseTahun;
    @SerializedName("total_purchase_bulan")
    @Expose
    private Double totalPurchaseBulan;
    @SerializedName("total_purchase_hari")
    @Expose
    private Double totalPurchaseHari;
    @SerializedName("total_dp_tahun")
    @Expose
    private Double totalDpTahun;
    @SerializedName("total_dp_bulan")
    @Expose
    private Double totalDpBulan;
    @SerializedName("total_dp_hari")
    @Expose
    private Double totalDpHari;
    @SerializedName("total_payment_tahun")
    @Expose
    private Double totalPaymentTahun;
    @SerializedName("total_payment_bulan")
    @Expose
    private Double totalPaymentBulan;
    @SerializedName("total_payment_hari")
    @Expose
    private Double totalPaymentHari;
    @SerializedName("total_hutang_tahun")
    @Expose
    private Double totalHutangTahun;
    @SerializedName("total_hutang_bulan")
    @Expose
    private Double totalHutangBulan;
    @SerializedName("total_hutang_hari")
    @Expose
    private Double totalHutangHari;

    public Double getTotalPurchaseTahun() {
        return totalPurchaseTahun;
    }

    public void setTotalPurchaseTahun(Double totalPurchaseTahun) {
        this.totalPurchaseTahun = totalPurchaseTahun;
    }

    public Double getTotalPurchaseBulan() {
        return totalPurchaseBulan;
    }

    public void setTotalPurchaseBulan(Double totalPurchaseBulan) {
        this.totalPurchaseBulan = totalPurchaseBulan;
    }

    public Double getTotalPurchaseHari() {
        return totalPurchaseHari;
    }

    public void setTotalPurchaseHari(Double totalPurchaseHari) {
        this.totalPurchaseHari = totalPurchaseHari;
    }

    public Double getTotalDpTahun() {
        return totalDpTahun;
    }

    public void setTotalDpTahun(Double totalDpTahun) {
        this.totalDpTahun = totalDpTahun;
    }

    public Double getTotalDpBulan() {
        return totalDpBulan;
    }

    public void setTotalDpBulan(Double totalDpBulan) {
        this.totalDpBulan = totalDpBulan;
    }

    public Double getTotalDpHari() {
        return totalDpHari;
    }

    public void setTotalDpHari(Double totalDpHari) {
        this.totalDpHari = totalDpHari;
    }

    public Double getTotalPaymentTahun() {
        return totalPaymentTahun;
    }

    public void setTotalPaymentTahun(Double totalPaymentTahun) {
        this.totalPaymentTahun = totalPaymentTahun;
    }

    public Double getTotalPaymentBulan() {
        return totalPaymentBulan;
    }

    public void setTotalPaymentBulan(Double totalPaymentBulan) {
        this.totalPaymentBulan = totalPaymentBulan;
    }

    public Double getTotalPaymentHari() {
        return totalPaymentHari;
    }

    public void setTotalPaymentHari(Double totalPaymentHari) {
        this.totalPaymentHari = totalPaymentHari;
    }

    public Double getTotalHutangTahun() {
        return totalHutangTahun;
    }

    public void setTotalHutangTahun(Double totalHutangTahun) {
        this.totalHutangTahun = totalHutangTahun;
    }

    public Double getTotalHutangBulan() {
        return totalHutangBulan;
    }

    public void setTotalHutangBulan(Double totalHutangBulan) {
        this.totalHutangBulan = totalHutangBulan;
    }

    public Double getTotalHutangHari() {
        return totalHutangHari;
    }

    public void setTotalHutangHari(Double totalHutangHari) {
        this.totalHutangHari = totalHutangHari;
    }

}