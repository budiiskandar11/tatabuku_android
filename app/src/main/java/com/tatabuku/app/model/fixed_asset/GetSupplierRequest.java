package com.tatabuku.app.model.fixed_asset;

public class GetSupplierRequest {
    private String jsonrpc = "2.0";
    private GetSupplierParams params;

    public GetSupplierRequest(GetSupplierParams params) {
        this.params = params;
    }
}
