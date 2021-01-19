package com.tatabuku.app.model.rekening;

public class SubmitEditBiayaList {
    public int account_id;
    public double amount;
    public String description;

    public SubmitEditBiayaList(int account_id, double amount, String description) {
        this.account_id = account_id;
        this.amount = amount;
        this.description = description;
    }

    public SubmitEditBiayaList(CatatBiayaList biaya) {
        this.account_id = biaya.getAccountId();
        this.amount = biaya.getAmount();
        this.description = biaya.getDescription();
    }
}
