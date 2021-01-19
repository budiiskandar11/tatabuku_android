package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardCustomerResponse {

    @SerializedName("result")
    @Expose
    private DashboardCustomerResult result;

    public DashboardCustomerResult getResult() {
        return result;
    }

    public void setResult(DashboardCustomerResult result) {
        this.result = result;
    }

}