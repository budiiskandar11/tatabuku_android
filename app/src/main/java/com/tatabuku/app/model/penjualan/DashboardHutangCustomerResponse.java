package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardHutangCustomerResponse {

    @SerializedName("result")
    @Expose
    private DashboardHutangCustomerResult result;

    public DashboardHutangCustomerResult getResult() {
        return result;
    }

    public void setResult(DashboardHutangCustomerResult result) {
        this.result = result;
    }

}