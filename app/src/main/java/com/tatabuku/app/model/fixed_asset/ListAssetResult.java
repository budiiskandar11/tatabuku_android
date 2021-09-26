package com.tatabuku.app.model.fixed_asset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ListAssetResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("header_data")
    @Expose
    private ListAssetHeader headerData;
    @SerializedName("List Assets")
    @Expose
    private List<ListAssetModel> listAssets = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ListAssetHeader getHeaderData() {
        return headerData;
    }

    public void setHeaderData(ListAssetHeader headerData) {
        this.headerData = headerData;
    }

    public List<ListAssetModel> getListAssets() {
        return listAssets;
    }

    public void setListAssets(List<ListAssetModel> listAssets) {
        this.listAssets = listAssets;
    }

}