package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailDPResult {

    @SerializedName("Total Piutang Customer")
    @Expose
    private CustomerDetailTotalData totalPiutangCustomer;
    @SerializedName("Total DP Customer")
    @Expose
    private CustomerDetailTotalData totalDpCustomer;
    @SerializedName("result")
    @Expose
    private CustomerDetailDPData result;

    public CustomerDetailTotalData getTotalPiutangCustomer() {
        return totalPiutangCustomer;
    }

    public void setTotalPiutangCustomer(CustomerDetailTotalData totalPiutangCustomer) {
        this.totalPiutangCustomer = totalPiutangCustomer;
    }

    public CustomerDetailTotalData getTotalDpCustomer() {
        return totalDpCustomer;
    }

    public void setTotalDpCustomer(CustomerDetailTotalData totalDpCustomer) {
        this.totalDpCustomer = totalDpCustomer;
    }

    public CustomerDetailDPData getResult() {
        return result;
    }

    public void setResult(CustomerDetailDPData result) {
        this.result = result;
    }

}