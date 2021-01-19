package com.tatabuku.app.model.fixed_asset;

public class GetPurchasesRequest {
    private String jsonrpc = "2.0";
    private GetPurchasesParams params;

    public GetPurchasesRequest(GetPurchasesParams params) {
        this.params = params;
    }
}
