package com.tatabuku.app.model.penjualan;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListCustomerResult {

    @SerializedName("datalist")
    @Expose
    private List<CustomerResult> datalist = null;

    public List<CustomerResult> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<CustomerResult> datalist) {
        this.datalist = datalist;
    }

}