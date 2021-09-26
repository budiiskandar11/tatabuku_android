package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
/*
{
    "result": [
        {
            "id": 1,
            "name": "Immediate Payment"
        }
    ]
}
 */
public class PaymentTermResponse {

    @SerializedName("result")
    @Expose
    private List<PaymentTerm> result = null;

    public List<PaymentTerm> getResult() {
        return result;
    }

    public void setResult(List<PaymentTerm> result) {
        this.result = result;
    }

}