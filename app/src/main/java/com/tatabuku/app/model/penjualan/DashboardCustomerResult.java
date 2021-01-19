package com.tatabuku.app.model.penjualan;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardCustomerResult {

    @SerializedName("list_customer_pav")
    @Expose
    private List<CustomerResult> listCustomerPav = null;
    @SerializedName("list_customer_non_pav")
    @Expose
    private ListCustomerResult listCustomerNonPav;

    @SerializedName("list_payment_pav")
    @Expose
    private List<CustomerResult> listPaymentPav = null;
    @SerializedName("list_payment_non_pav")
    @Expose
    private ListCustomerResult listPaymentNonPav;

    @SerializedName("list_dp_customer_pav")
    @Expose
    private List<CustomerResult> listDpPav = null;
    @SerializedName("list_dp_customer_non_pav")
    @Expose
    private ListCustomerResult listDpNonPav;

    public List<CustomerResult> getListCustomerPav() {
        return listCustomerPav;
    }

    public void setListCustomerPav(List<CustomerResult> listCustomerPav) {
        this.listCustomerPav = listCustomerPav;
    }

    public ListCustomerResult getListCustomerNonPav() {
        return listCustomerNonPav;
    }

    public void setListCustomerNonPav(ListCustomerResult listCustomerNonPav) {
        this.listCustomerNonPav = listCustomerNonPav;
    }

    public List<CustomerResult> getListPaymentPav() {
        return listPaymentPav;
    }

    public void setListPaymentPav(List<CustomerResult> listPaymentPav) {
        this.listPaymentPav = listPaymentPav;
    }

    public ListCustomerResult getListPaymentNonPav() {
        return listPaymentNonPav;
    }

    public void setListPaymentNonPav(ListCustomerResult listPaymentNonPav) {
        this.listPaymentNonPav = listPaymentNonPav;
    }

    public List<CustomerResult> getListDpPav() {
        return listDpPav;
    }

    public void setListDpPav(List<CustomerResult> listDpPav) {
        this.listDpPav = listDpPav;
    }

    public ListCustomerResult getListDpNonPav() {
        return listDpNonPav;
    }

    public void setListDpNonPav(ListCustomerResult listDpNonPav) {
        this.listDpNonPav = listDpNonPav;
    }
}