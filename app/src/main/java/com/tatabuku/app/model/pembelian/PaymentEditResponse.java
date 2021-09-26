
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentEditResponse {

    @SerializedName("result")
    @Expose
    private PaymentEditResult result;

    public PaymentEditResult getResult() {
        return result;
    }

    public void setResult(PaymentEditResult result) {
        this.result = result;
    }

}

