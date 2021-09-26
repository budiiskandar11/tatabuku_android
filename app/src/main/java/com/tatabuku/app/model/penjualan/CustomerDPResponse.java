package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDPResponse {

    @SerializedName("result")
    @Expose
    private CustomerDPResult result;

    public CustomerDPResult getResult() {
        return result;
    }

    public void setResult(CustomerDPResult result) {
        this.result = result;
    }

}