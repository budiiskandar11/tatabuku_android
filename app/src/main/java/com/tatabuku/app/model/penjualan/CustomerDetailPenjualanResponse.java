package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailPenjualanResponse {

    @SerializedName("result")
    @Expose
    private CustomerDetailPenjualanResult result;

    public CustomerDetailPenjualanResult getResult() {
        return result;
    }

    public void setResult(CustomerDetailPenjualanResult result) {
        this.result = result;
    }

}