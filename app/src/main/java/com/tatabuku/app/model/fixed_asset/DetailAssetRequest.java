package com.tatabuku.app.model.fixed_asset;

public class DetailAssetRequest {

    private String jsonrpc = "2.0";
    private DetailAssetParams params;

    public DetailAssetRequest(DetailAssetParams params) {
        this.params = params;
    }
}
