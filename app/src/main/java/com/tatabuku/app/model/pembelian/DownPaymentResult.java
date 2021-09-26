package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownPaymentResult {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("dp_type")
    @Expose
    private String dpType;
    @SerializedName("amount_total_signed")
    @Expose
    private Double amountDp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDpType() {
        return dpType;
    }

    public void setDpType(String dpType) {
        this.dpType = dpType;
    }

    public Double getAmountDp() {
        return amountDp;
    }

    public void setAmountDp(Double amountDp) {
        this.amountDp = amountDp;
    }

}