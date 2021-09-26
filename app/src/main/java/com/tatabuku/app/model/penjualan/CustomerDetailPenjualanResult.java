package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailPenjualanResult {

    @SerializedName("Total Penjualan Customer")
    @Expose
    private CustomerDetailTotalData totalPenjualanCustomer;
    @SerializedName("result")
    @Expose
    private CustomerDetailPenjualanData result;

    public CustomerDetailTotalData getTotalPenjualanCustomer() {
        return totalPenjualanCustomer;
    }

    public void setTotalPenjualanCustomer(CustomerDetailTotalData totalPenjualanCustomer) {
        this.totalPenjualanCustomer = totalPenjualanCustomer;
    }

    public CustomerDetailPenjualanData getResult() {
        return result;
    }

    public void setResult(CustomerDetailPenjualanData result) {
        this.result = result;
    }

}