package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengirimanBarangPartnerData {

    @SerializedName("partner_id")
    @Expose
    private String partnerId;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("order_no")
    @Expose
    private String orderNo;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}