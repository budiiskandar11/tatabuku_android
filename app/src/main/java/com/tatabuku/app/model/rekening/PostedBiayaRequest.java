package com.tatabuku.app.model.rekening;

public class PostedBiayaRequest {
    private String jsonrpc = "2.0";
    private PostedBiayaParams params;

    public PostedBiayaRequest(PostedBiayaParams params) {
        this.params = params;
    }
}
