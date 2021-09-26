package com.tatabuku.app.model.fixed_asset;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListAssetResponse {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("result")
    @Expose
    private ListAssetResult result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public ListAssetResult getResult() {
        return result;
    }

    public void setResult(ListAssetResult result) {
        this.result = result;
    }

}