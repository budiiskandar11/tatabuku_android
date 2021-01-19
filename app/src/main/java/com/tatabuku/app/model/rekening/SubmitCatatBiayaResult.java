package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitCatatBiayaResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("voucher_id")
    @Expose
    private Integer voucherId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

}