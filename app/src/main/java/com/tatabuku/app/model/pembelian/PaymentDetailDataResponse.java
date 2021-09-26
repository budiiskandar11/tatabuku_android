
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentDetailDataResponse {

    @SerializedName("result")
    @Expose
    private PaymentDetailDataResult result;

    public PaymentDetailDataResult getResult() {
        return result;
    }

    public void setResult(PaymentDetailDataResult result) {
        this.result = result;
    }

}

