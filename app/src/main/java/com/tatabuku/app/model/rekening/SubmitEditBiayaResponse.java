package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitEditBiayaResponse {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("result")
    @Expose
    private SubmitEditBiayaResult result;

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

    public SubmitEditBiayaResult getResult() {
        return result;
    }

    public void setResult(SubmitEditBiayaResult result) {
        this.result = result;
    }

}