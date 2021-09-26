package com.tatabuku.app.model.pembelian;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.model.penjualan.CustomerDetailTotalData;

public class PembayaranResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Partner_Data")
    @Expose
    private CustomerDetailTotalData partnerData;
    @SerializedName("payment_list")
    @Expose
    private List<PembayaranItem> paymentList = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomerDetailTotalData getPartnerData() {
        return partnerData;
    }

    public void setPartnerData(CustomerDetailTotalData partnerData) {
        this.partnerData = partnerData;
    }

    public List<PembayaranItem> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<PembayaranItem> paymentList) {
        this.paymentList = paymentList;
    }

}