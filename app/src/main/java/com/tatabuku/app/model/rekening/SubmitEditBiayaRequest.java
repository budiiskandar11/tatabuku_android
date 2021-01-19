package com.tatabuku.app.model.rekening;

public class SubmitEditBiayaRequest {
    public String jsonrpc = "2.0";
    public SubmitEditBiayaParams params;

    public SubmitEditBiayaRequest(SubmitEditBiayaParams params) {
        this.params = params;
    }
}
