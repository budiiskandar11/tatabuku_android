package com.tatabuku.app.model.rekening;

public class CatatBiayaRequest {
    private String jsonrpc = "2.0";
    private CatatBiayaParams params;

    public CatatBiayaRequest(CatatBiayaParams params) {
        this.params = params;
    }
}