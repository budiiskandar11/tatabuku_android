package com.tatabuku.app.model.pembelian;

public class SubmitPaymentBank {
    private Integer id;
    private Double amount;

    public SubmitPaymentBank(Integer id, Double amount) {
        this.id = id;
        this.amount = amount;
    }
}
