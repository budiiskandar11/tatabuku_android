package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CatatBiayaList implements Serializable {

    @SerializedName("account_id")
    @Expose
    private Integer accountId;
    @SerializedName("account_name")
    @Expose
    private String accountName;

    private Double amount;

    private String description;

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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public SubmitCatatBiayaList toSubmit() {
        return new SubmitCatatBiayaList(this);
    }

    public SubmitEditBiayaList toEdit() {
        return new SubmitEditBiayaList(this);
    }

    public CatatBiayaList (PostedBiayaList biaya) {
        this.amount = biaya.getAmount();
        this.accountName = biaya.getJudul();
        this.description = biaya.getDescription();
        this.accountId = biaya.getAccountId();
    }
}
