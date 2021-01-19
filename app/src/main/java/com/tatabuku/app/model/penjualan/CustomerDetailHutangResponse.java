package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailHutangResponse {

    @SerializedName("result")
    @Expose
    private CustomerDetailHutangResult result;

    public CustomerDetailHutangResult getResult() {
        return result;
    }

    public void setResult(CustomerDetailHutangResult result) {
        this.result = result;
    }

}