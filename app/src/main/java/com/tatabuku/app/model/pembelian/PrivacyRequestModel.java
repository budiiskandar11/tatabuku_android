package com.tatabuku.app.model.pembelian;

public class PrivacyRequestModel {
    private String jsonrpc = "2.0";

    private PrivacyPolicyParams params;

    public PrivacyRequestModel() {params = new PrivacyPolicyParams();}
}
