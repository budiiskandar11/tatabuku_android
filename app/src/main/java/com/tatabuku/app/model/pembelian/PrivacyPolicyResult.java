package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivacyPolicyResult {
    @SerializedName("terms_and_condition")
    @Expose
    private String terms;
    @SerializedName("privacy_policy")
    @Expose
    private String privacy;

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}