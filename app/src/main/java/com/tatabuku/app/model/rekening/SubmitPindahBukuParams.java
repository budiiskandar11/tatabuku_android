package com.tatabuku.app.model.rekening;

public class SubmitPindahBukuParams {
    public int bank_from;
    public int bank_to;
    public int amount;
    public String date;

    public SubmitPindahBukuParams(int bank_from, int bank_to, int amount, String date) {
        this.bank_from = bank_from;
        this.bank_to = bank_to;
        this.amount = amount;
        this.date = date;
    }
}
