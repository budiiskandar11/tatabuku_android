package com.tatabuku.app.model.titip_jurnal;

public class LoadTitipJurnalRequest {
    private String jsonrpc = "2.0";
    private LoadTitipJurnalParams params;

    public LoadTitipJurnalRequest(LoadTitipJurnalParams params) {
        this.params = params;
    }
}
