package com.tatabuku.app.model.rekening;

public class PindahBukuRequest {
    private String jsonrpc = "2.0";
    private PindahBukuParams params;

    public PindahBukuRequest(PindahBukuParams params) {
        this.params = params;
    }
}
