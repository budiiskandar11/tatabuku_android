
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailResponse {

    @SerializedName("result")
    @Expose
    private List<OrderDetailResult> result = null;

    public List<OrderDetailResult> getResult() {
        return result;
    }

    public void setResult(List<OrderDetailResult> result) {
        this.result = result;
    }

}