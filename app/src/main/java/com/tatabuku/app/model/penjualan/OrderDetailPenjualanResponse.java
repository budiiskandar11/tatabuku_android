package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailPenjualanResponse {

    @SerializedName("result")
    @Expose
    private OrderDetailPenjualanResult result;

    public OrderDetailPenjualanResult getResult() {
        return result;
    }

    public void setResult(OrderDetailPenjualanResult result) {
        this.result = result;
    }

}