package com.tatabuku.app.model.fixed_asset;

public class ListAssetRequest {
    private String jsonrpc = "2.0";
    private ListAssetParams params;

    public ListAssetRequest(ListAssetParams params) {
        this.params = params;
    }
}
