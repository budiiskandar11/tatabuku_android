package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DPData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("partner_id")
    @Expose
    private Integer partnerId;
    @SerializedName("partner_name")
    @Expose
    private String partnerName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("dp_number")
    @Expose
    private String dpNumber;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDpNumber() {
        return dpNumber;
    }

    public void setDpNumber(String dpNumber) {
        this.dpNumber = dpNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}