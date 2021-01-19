package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitEditBiayaResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}