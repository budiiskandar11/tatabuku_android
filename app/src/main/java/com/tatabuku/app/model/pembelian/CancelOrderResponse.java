
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelOrderResponse {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("result")
    @Expose
    private CancelOrderResult result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public CancelOrderResult getResult() {
        return result;
    }

    public void setResult(CancelOrderResult result) {
        this.result = result;
    }

}