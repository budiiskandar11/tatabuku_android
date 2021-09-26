package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDPResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("customer_image")
    @Expose
    private String customerImage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("amount_dp")
    @Expose
    private Double amountDp;
    @SerializedName("rekening")
    @Expose
    private String rekening;
    @SerializedName("dp_type")
    @Expose
    private String dpType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmountDp() {
        return amountDp;
    }

    public void setAmountDp(Double amountDp) {
        this.amountDp = amountDp;
    }

    public String getRekening() {
        return rekening;
    }

    public void setRekening(String rekening) {
        this.rekening = rekening;
    }

    public String getDpType() {
        return dpType;
    }

    public void setDpType(String dpType) {
        this.dpType = dpType;
    }
}