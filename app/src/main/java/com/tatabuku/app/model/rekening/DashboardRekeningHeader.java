package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardRekeningHeader {

    @SerializedName("total_bank_kas")
    @Expose
    private Double totalBankKas;
    @SerializedName("total_debit")
    @Expose
    private Double totalDebit;
    @SerializedName("total_credit")
    @Expose
    private Double totalCredit;
    @SerializedName("tanggal_update")
    @Expose
    private String tanggalUpdate;

    public Double getTotalBankKas() {
        return totalBankKas;
    }

    public void setTotalBankKas(Double totalBankKas) {
        this.totalBankKas = totalBankKas;
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

    public String getTanggalUpdate() {
        return tanggalUpdate;
    }

    public void setTanggalUpdate(String tanggalUpdate) {
        this.tanggalUpdate = tanggalUpdate;
    }

}
