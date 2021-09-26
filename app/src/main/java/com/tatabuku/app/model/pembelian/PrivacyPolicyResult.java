package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivacyPolicyResult {
    @SerializedName("result")
    @Expose
    private PrivacyPolicyDataResult dataResult;
    @SerializedName("message")
    @Expose
    private String message;

    public PrivacyPolicyDataResult getDataResult() {
        return dataResult;
    }

    public void setDataResult(PrivacyPolicyDataResult dataResult) {
        this.dataResult = dataResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
