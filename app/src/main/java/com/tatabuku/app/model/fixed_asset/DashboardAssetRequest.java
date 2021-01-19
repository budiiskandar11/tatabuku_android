package com.tatabuku.app.model.fixed_asset;


public class DashboardAssetRequest {
    private String jsonrpc = "2.0";
    private DashboardAssetParams params;

    public DashboardAssetRequest() {
        params = new DashboardAssetParams();
    }
}