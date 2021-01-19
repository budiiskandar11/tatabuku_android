package com.tatabuku.app.model.fixed_asset;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailAssetResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("asset_header")
    @Expose
    private DetailAssetHeader assetHeader;
    @SerializedName("asset_info")
    @Expose
    private DetailAssetInfo assetInfo;
    @SerializedName("list_depresiasi")
    @Expose
    private List<DetailAssetDepresiasi> listDepresiasi = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DetailAssetHeader getAssetHeader() {
        return assetHeader;
    }

    public void setAssetHeader(DetailAssetHeader assetHeader) {
        this.assetHeader = assetHeader;
    }

    public DetailAssetInfo getAssetInfo() {
        return assetInfo;
    }

    public void setAssetInfo(DetailAssetInfo assetInfo) {
        this.assetInfo = assetInfo;
    }

    public List<DetailAssetDepresiasi> getListDepresiasi() {
        return listDepresiasi;
    }

    public void setListDepresiasi(List<DetailAssetDepresiasi> listDepresiasi) {
        this.listDepresiasi = listDepresiasi;
    }

}