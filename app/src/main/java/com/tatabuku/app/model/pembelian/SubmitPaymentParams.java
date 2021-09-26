package com.tatabuku.app.model.pembelian;

public class SubmitPaymentParams {
    private Integer invoice_id;
    private String payment_date;
    private Double dp_payment;
    private Double potongan_pembayaran;
    private SubmitPaymentBank bank_id;

    public SubmitPaymentParams(Integer invoice_id, String payment_date, Double dp_payment, Double potongan_pembayaran, SubmitPaymentBank bank_id) {
        this.invoice_id = invoice_id;
        this.payment_date = payment_date;
        this.dp_payment = dp_payment;
        this.potongan_pembayaran = potongan_pembayaran;
        this.bank_id = bank_id;
    }
}
