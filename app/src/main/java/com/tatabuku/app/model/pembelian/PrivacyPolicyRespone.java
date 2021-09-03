package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.model.fixed_asset.DashboardAssetResult;

public class PrivacyPolicyRespone {
    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("result")
    @Expose
    private PrivacyPolicyResult result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public PrivacyPolicyResult getResult() {
        return result;
    }
}
