package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardTotalPenjualanHeader {

    @SerializedName("total_sale_tahun")
    @Expose
    private Double totalSaleTahun;
    @SerializedName("target_sale_tahun")
    @Expose
    private Double targetSaleTahun;
    @SerializedName("total_sale_bulan")
    @Expose
    private Double totalSaleBulan;
    @SerializedName("target_sale_bulan")
    @Expose
    private Double targetSaleBulan;
    @SerializedName("total_sale_hari")
    @Expose
    private Double totalSaleHari;
    @SerializedName("target_sale_hari")
    @Expose
    private Double targetSaleHari;

    public Double getTotalSaleTahun() {
        return totalSaleTahun;
    }

    public void setTotalSaleTahun(Double totalSaleTahun) {
        this.totalSaleTahun = totalSaleTahun;
    }

    public Double getTargetSaleTahun() {
        return targetSaleTahun;
    }

    public void setTargetSaleTahun(Double targetSaleTahun) {
        this.targetSaleTahun = targetSaleTahun;
    }

    public Double getTotalSaleBulan() {
        return totalSaleBulan;
    }

    public void setTotalSaleBulan(Double totalSaleBulan) {
        this.totalSaleBulan = totalSaleBulan;
    }

    public Double getTargetSaleBulan() {
        return targetSaleBulan;
    }

    public void setTargetSaleBulan(Double targetSaleBulan) {
        this.targetSaleBulan = targetSaleBulan;
    }

    public Double getTotalSaleHari() {
        return totalSaleHari;
    }

    public void setTotalSaleHari(Double totalSaleHari) {
        this.totalSaleHari = totalSaleHari;
    }

    public Double getTargetSaleHari() {
        return targetSaleHari;
    }

    public void setTargetSaleHari(Double targetSaleHari) {
        this.targetSaleHari = targetSaleHari;
    }

}