package com.tatabuku.app.model.fixed_asset;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardAssetResult {

    @SerializedName("asset_dashboard_header")
    @Expose
    private DashboardAssetHeader assetDashboardHeader;
    @SerializedName("asset_dashboard_list")
    @Expose
    private List<DashboardAssetModel> assetDashboardList = null;

    public DashboardAssetHeader getAssetDashboardHeader() {
        return assetDashboardHeader;
    }

    public void setAssetDashboardHeader(DashboardAssetHeader assetDashboardHeader) {
        this.assetDashboardHeader = assetDashboardHeader;
    }

    public List<DashboardAssetModel> getAssetDashboardList() {
        return assetDashboardList;
    }

    public void setAssetDashboardList(List<DashboardAssetModel> assetDashboardList) {
        this.assetDashboardList = assetDashboardList;
    }

}

