package com.tatabuku.app.model.rekening;

public class DeleteBiayaRequest {
    public String jsonrpc = "2.0";
    public DeleteBiayaParams params;

    public DeleteBiayaRequest(DeleteBiayaParams params) {
        this.params = params;
    }
}
