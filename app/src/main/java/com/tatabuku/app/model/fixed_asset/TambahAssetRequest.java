package com.tatabuku.app.model.fixed_asset;

public class TambahAssetRequest {
    private String jsonrpc = "2.0";
    private TambahAssetParams params;

    public TambahAssetRequest(TambahAssetParams params) {
        this.params = params;
    }
}
