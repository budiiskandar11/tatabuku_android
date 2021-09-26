package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PindahBukuHeader {

    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("total_saldo")
    @Expose
    private Double totalSaldo;
    @SerializedName("total_debit")
    @Expose
    private Double totalDebit;
    @SerializedName("total_credit")
    @Expose
    private Double totalCredit;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(Double totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public Double getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(Double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public Double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(Double totalCredit) {
        this.totalCredit = totalCredit;
    }

}