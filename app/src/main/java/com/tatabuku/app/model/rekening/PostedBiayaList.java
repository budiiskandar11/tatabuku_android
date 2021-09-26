package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostedBiayaList implements Serializable {

    @SerializedName("line_id")
    @Expose
    private Integer lineId;
    @SerializedName("account_id")
    @Expose
    private Integer accountId;
    @SerializedName("qty")
    @Expose
    private Double qty;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("judul")
    @Expose
    private String judul;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public CatatBiayaList toCatatBiaya() {
        return new CatatBiayaList(this);
    }
}