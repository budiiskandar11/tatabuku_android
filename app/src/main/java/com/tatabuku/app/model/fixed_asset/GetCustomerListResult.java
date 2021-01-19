package com.tatabuku.app.model.fixed_asset;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCustomerListResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private List<GetCustomerModel> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetCustomerModel> getResult() {
        return result;
    }

    public void setResult(List<GetCustomerModel> result) {
        this.result = result;
    }

}