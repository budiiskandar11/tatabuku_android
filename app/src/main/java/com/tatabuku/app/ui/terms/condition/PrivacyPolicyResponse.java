package com.tatabuku.app.ui.terms.condition;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivacyPolicyResponse {

    @SerializedName("deskripsi")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }
}

