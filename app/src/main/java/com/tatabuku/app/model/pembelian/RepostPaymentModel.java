package com.tatabuku.app.model.pembelian;

/*
{"jsonrpc": "2.0", "params": {"payment_id":4,
                                "payment_date" : "2020-09-28",
                                "bank_id":{"id": 7,
                                            "amount" : 5000},
                                "dp_payment" :10000
                                }
}
 */
public class RepostPaymentModel {
    private String jsonrpc = "2.0";
    private RepostPaymentParams params;

    public RepostPaymentModel(RepostPaymentParams params) {
        this.params = params;
    }
}
