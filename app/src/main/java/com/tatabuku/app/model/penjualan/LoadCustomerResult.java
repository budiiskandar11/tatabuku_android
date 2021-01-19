package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadCustomerResult {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("partner_date")
    @Expose
    private LoadCustomerData partnerDate;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoadCustomerData getPartnerDate() {
        return partnerDate;
    }

    public void setPartnerDate(LoadCustomerData partnerDate) {
        this.partnerDate = partnerDate;
    }

}