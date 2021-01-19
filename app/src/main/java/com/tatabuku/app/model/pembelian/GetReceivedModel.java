package com.tatabuku.app.model.pembelian;

/*
{"jsonrpc": "2.0", "params": {"purchase_id":22
                                }
}
 */
public class GetReceivedModel {
    private String jsonrpc = "2.0";
    private GetReceivedParams params;

    public GetReceivedModel(GetReceivedParams params) {
        this.params = params;
    }
}
