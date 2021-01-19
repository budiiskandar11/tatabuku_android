package com.tatabuku.app.model.pembelian;

public class RepostPaymentParams {
    private Integer payment_id;
    private String payment_date;
    private Double dp_payment;
    private Double potongan_pembayaran;
    private SubmitPaymentBank bank_id;

    public RepostPaymentParams(Integer payment_id, String payment_date, Double dp_payment, Double potongan_pembayaran, SubmitPaymentBank bank_id) {
        this.payment_id = payment_id;
        this.payment_date = payment_date;
        this.dp_payment = dp_payment;
        this.potongan_pembayaran = potongan_pembayaran;
        this.bank_id = bank_id;
    }
}
