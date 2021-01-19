
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentDataResponse {

    @SerializedName("result")
    @Expose
    private PaymentDataResult result;

    public PaymentDataResult getResult() {
        return result;
    }

    public void setResult(PaymentDataResult result) {
        this.result = result;
    }

}

