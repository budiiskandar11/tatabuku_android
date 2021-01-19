package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardCustomerTotalResult {

    @SerializedName("Result")
    @Expose
    private DashboardCustomerTotalData result;
    @SerializedName("Total_Piutang")
    @Expose
    private DashboardCustomerTotalHutangData totalHutang;

    public DashboardCustomerTotalData getResult() {
        return result;
    }

    public void setResult(DashboardCustomerTotalData result) {
        this.result = result;
    }

    public DashboardCustomerTotalHutangData getTotalHutang() {
        return totalHutang;
    }

    public void setTotalHutang(DashboardCustomerTotalHutangData totalHutang) {
        this.totalHutang = totalHutang;
    }

}