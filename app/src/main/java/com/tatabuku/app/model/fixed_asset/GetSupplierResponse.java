package com.tatabuku.app.model.fixed_asset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSupplierResponse {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("result")
    @Expose
    private GetSupplierResult result;

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

    public GetSupplierResult getResult() {
        return result;
    }

    public void setResult(GetSupplierResult result) {
        this.result = result;
    }

}
