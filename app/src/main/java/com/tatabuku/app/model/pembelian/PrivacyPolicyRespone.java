package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.model.fixed_asset.DashboardAssetResult;
import com.tatabuku.app.model.home.HomepageDataResult;

public class PrivacyPolicyRespone {
    @SerializedName("result")
    @Expose
    private PrivacyPolicyResult result;

    public PrivacyPolicyResult getResult() {
        return result;
    }

    public void setResult(PrivacyPolicyResult result) {
        this.result = result;
    }
}
