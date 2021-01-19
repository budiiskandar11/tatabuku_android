package com.tatabuku.app.model.pembelian;

/*
{"jsonrpc": "2.0", "params": {"id":7
                                }
}
 */
public class SubmitOrderModel {
    private String jsonrpc = "2.0";
    private SubmitOrderParams params;

    public SubmitOrderModel(SubmitOrderParams params) {
        this.params = params;
    }
}
