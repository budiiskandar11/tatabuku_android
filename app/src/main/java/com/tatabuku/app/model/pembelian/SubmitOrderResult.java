package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitOrderResult {

    @SerializedName(value = "message", alternate = {"Message"})
    @Expose
    private String message;
    @SerializedName("ID")
    @Expose
    private Integer iD;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

}