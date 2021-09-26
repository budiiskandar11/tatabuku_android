package com.tatabuku.app.model.rekening;

public class RekeningDetailRequest {
    private String jsonrpc = "2.0";
    private RekeningDetailParams params;

    public RekeningDetailRequest(RekeningDetailParams params) {
        this.params = params;
    }
}