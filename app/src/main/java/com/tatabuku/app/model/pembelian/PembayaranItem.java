package com.tatabuku.app.model.pembelian;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PembayaranItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("total_payment_amount")
    @Expose
    private Double totalPaymentAmount;
    @SerializedName("payment_by_dp")
    @Expose
    private Double paymentByDp;
    @SerializedName("payment_by_bank/cash")
    @Expose
    private Double paymentByBankCash;
    @SerializedName("bank_cash_id")
    @Expose
    private Integer bankCashId;
    @SerializedName("bank_cash_name")
    @Expose
    private String bankCashName;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("payment_no")
    @Expose
    private String paymentNo;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(Double totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public Double getPaymentByDp() {
        return paymentByDp;
    }

    public void setPaymentByDp(Double paymentByDp) {
        this.paymentByDp = paymentByDp;
    }

    public Double getPaymentByBankCash() {
        return paymentByBankCash;
    }

    public void setPaymentByBankCash(Double paymentByBankCash) {
        this.paymentByBankCash = paymentByBankCash;
    }

    public Integer getBankCashId() {
        return bankCashId;
    }

    public void setBankCashId(Integer bankCashId) {
        this.bankCashId = bankCashId;
    }

    public String getBankCashName() {
        return bankCashName;
    }

    public void setBankCashName(String bankCashName) {
        this.bankCashName = bankCashName;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}