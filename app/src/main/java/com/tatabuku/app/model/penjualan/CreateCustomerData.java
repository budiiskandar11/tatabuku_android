package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateCustomerData {

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

}