package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailHutangResult {

    @SerializedName("Total Piutang Customer")
    @Expose
    private CustomerDetailTotalData totalPiutangCustomer;
    @SerializedName("result")
    @Expose
    private CustomerDetailHutangData result;

    public CustomerDetailTotalData getTotalPiutangCustomer() {
        return totalPiutangCustomer;
    }

    public void setTotalPiutangCustomer(CustomerDetailTotalData totalPiutangCustomer) {
        this.totalPiutangCustomer = totalPiutangCustomer;
    }

    public CustomerDetailHutangData getResult() {
        return result;
    }

    public void setResult(CustomerDetailHutangData result) {
        this.result = result;
    }

}