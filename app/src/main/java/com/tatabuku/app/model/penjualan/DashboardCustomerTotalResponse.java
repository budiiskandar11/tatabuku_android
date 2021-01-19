package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardCustomerTotalResponse {

    @SerializedName("result")
    @Expose
    private DashboardCustomerTotalResult result;

    public DashboardCustomerTotalResult getResult() {
        return result;
    }

    public void setResult(DashboardCustomerTotalResult result) {
        this.result = result;
    }

}