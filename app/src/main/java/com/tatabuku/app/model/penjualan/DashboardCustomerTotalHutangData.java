package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardCustomerTotalHutangData {

    @SerializedName("piutang_total")
    @Expose
    private Double piutangTotal;
    @SerializedName("total_piutang_60")
    @Expose
    private Double totalPiutang60;
    @SerializedName("total_piutang_30")
    @Expose
    private Double totalPiutang30;
    @SerializedName("total_piutang_hari")
    @Expose
    private Double totalPiutangHari;

    public Double getPiutangTotal() {
        return piutangTotal;
    }

    public void setPiutangTotal(Double piutangTotal) {
        this.piutangTotal = piutangTotal;
    }

    public Double getTotalPiutang60() {
        return totalPiutang60;
    }

    public void setTotalPiutang60(Double totalPiutang60) {
        this.totalPiutang60 = totalPiutang60;
    }

    public Double getTotalPiutang30() {
        return totalPiutang30;
    }

    public void setTotalPiutang30(Double totalPiutang30) {
        this.totalPiutang30 = totalPiutang30;
    }

    public Double getTotalPiutangHari() {
        return totalPiutangHari;
    }

    public void setTotalPiutangHari(Double totalPiutangHari) {
        this.totalPiutangHari = totalPiutangHari;
    }

}