package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardTotalPenjualanResponse {

    @SerializedName("result")
    @Expose
    private DashboardTotalPenjualanResult result;

    public DashboardTotalPenjualanResult getResult() {
        return result;
    }

    public void setResult(DashboardTotalPenjualanResult result) {
        this.result = result;
    }

}