
package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.model.pembelian.SubmitOrderResult;

public class CreateCustomerResponse {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("result")
    @Expose
    private CreateCustomerResult result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public CreateCustomerResult getResult() {
        return result;
    }

    public void setResult(CreateCustomerResult result) {
        this.result = result;
    }

}