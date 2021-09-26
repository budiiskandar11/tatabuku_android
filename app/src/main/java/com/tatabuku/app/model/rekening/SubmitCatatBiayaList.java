package com.tatabuku.app.model.rekening;

public class SubmitCatatBiayaList {
    public int account_id;
    public Double amount;
    public String description;

    public SubmitCatatBiayaList(int account_id, double amount, String description) {
        this.account_id = account_id;
        this.amount = amount;
        this.description = description;
    }

    public SubmitCatatBiayaList(CatatBiayaList biaya) {
        this.account_id = biaya.getAccountId();
        this.amount = biaya.getAmount();
        this.description = biaya.getDescription() != null ? biaya.getDescription() : "";
    }
}
