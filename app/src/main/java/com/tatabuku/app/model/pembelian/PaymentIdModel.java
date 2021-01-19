package com.tatabuku.app.model.pembelian;

/*
{"jsonrpc": "2.0", "params": {"invoice_id":2}
}
 */
public class PaymentIdModel {
    private String jsonrpc = "2.0";
    private PaymentIdParams params;

    public PaymentIdModel(PaymentIdParams params) {
        this.params = params;
    }
}
