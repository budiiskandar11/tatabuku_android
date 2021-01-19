package com.tatabuku.app.model.pembelian;

/*
{"jsonrpc": "2.0", "params": {"invoice_id":2}
}
 */
public class PaymentDataModel {
    private String jsonrpc = "2.0";
    private PaymentDataParams params;

    public PaymentDataModel(PaymentDataParams params) {
        this.params = params;
    }
}
