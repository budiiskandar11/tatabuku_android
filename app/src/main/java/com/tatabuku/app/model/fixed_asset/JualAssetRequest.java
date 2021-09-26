package com.tatabuku.app.model.fixed_asset;

public class JualAssetRequest {
    private String jsonrpc = "2.0";
    private JualAssetParams params;

    public JualAssetRequest(JualAssetParams params) {
        this.params = params;
    }
}
