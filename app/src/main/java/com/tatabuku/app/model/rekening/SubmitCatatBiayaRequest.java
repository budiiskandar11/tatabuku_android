package com.tatabuku.app.model.rekening;

public class SubmitCatatBiayaRequest {
    private String jsonrpc = "2.0";
    private SubmitCatatBiayaParams params;

    public SubmitCatatBiayaRequest(SubmitCatatBiayaParams params) {
        this.params = params;
    }
}
