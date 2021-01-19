package com.tatabuku.app.model.rekening;

public class SubmitPindahBukuRequest {
    private String jsonrpc = "2.0";
    private SubmitPindahBukuParams params;

    public SubmitPindahBukuRequest(SubmitPindahBukuParams params) {
        this.params = params;
    }
}