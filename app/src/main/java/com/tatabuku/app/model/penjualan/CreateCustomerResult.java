package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateCustomerResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private CreateCustomerData result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CreateCustomerData getResult() {
        return result;
    }

    public void setResult(CreateCustomerData result) {
        this.result = result;
    }
}