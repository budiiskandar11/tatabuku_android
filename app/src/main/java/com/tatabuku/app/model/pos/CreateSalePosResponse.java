package com.tatabuku.app.model.pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.model.fixed_asset.ListAssetResult;

public class CreateSalePosResponse {
    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("result")
    @Expose
    private CreateSalePosResult result;

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

    public CreateSalePosResult getResult() {
        return result;
    }

    public void setResult(CreateSalePosResult result) {
        this.result = result;
    }
}
