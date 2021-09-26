package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.model.pembelian.PembayaranResult;

public class PembayaranResponse {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("result")
    @Expose
    private PembayaranResult result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PembayaranResult getResult() {
        return result;
    }

    public void setResult(PembayaranResult result) {
        this.result = result;
    }

}

