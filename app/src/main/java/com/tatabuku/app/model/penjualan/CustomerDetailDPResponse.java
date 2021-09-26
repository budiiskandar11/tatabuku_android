package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailDPResponse {

    @SerializedName("result")
    @Expose
    private CustomerDetailDPResult result;

    public CustomerDetailDPResult getResult() {
        return result;
    }

    public void setResult(CustomerDetailDPResult result) {
        this.result = result;
    }

}