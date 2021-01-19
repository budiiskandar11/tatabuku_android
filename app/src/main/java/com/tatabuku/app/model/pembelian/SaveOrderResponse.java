
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveOrderResponse {

    @SerializedName("result")
    @Expose
    private SaveOrderResult result;

    public SaveOrderResult getResult() {
        return result;
    }

    public void setResult(SaveOrderResult result) {
        this.result = result;
    }

}