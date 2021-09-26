package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadCustomerResponse {

    @SerializedName("result")
    @Expose
    private LoadCustomerResult result;

    public LoadCustomerResult getResult() {
        return result;
    }

    public void setResult(LoadCustomerResult result) {
        this.result = result;
    }

}


