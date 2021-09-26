package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardHutangCustomerResult {

    @SerializedName("list_customer_pav")
    @Expose
    private List<CustomerResult> listCustomerPav = null;
    @SerializedName("list_customer_non_pav")
    @Expose
    private List<CustomerResult> listCustomerNonPav;

    public List<CustomerResult> getListCustomerPav() {
        return listCustomerPav;
    }

    public void setListCustomerPav(List<CustomerResult> listCustomerPav) {
        this.listCustomerPav = listCustomerPav;
    }

    public List<CustomerResult> getListCustomerNonPav() {
        return listCustomerNonPav;
    }

    public void setListCustomerNonPav(List<CustomerResult> listCustomerNonPav) {
        this.listCustomerNonPav = listCustomerNonPav;
    }

}