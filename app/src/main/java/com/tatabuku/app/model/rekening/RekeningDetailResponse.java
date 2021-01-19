package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RekeningDetailResponse {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("result")
    @Expose
    private RekeningDetailResult result;

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

    public RekeningDetailResult getResult() {
        return result;
    }

    public void setResult(RekeningDetailResult result) {
        this.result = result;
    }

}
