package com.tatabuku.app.model.titip_jurnal;

public class DetailTitipJurnalRequest {
    private String jsonrpc = "2.0";
    private DetailTitipJurnalParams params;

    public DetailTitipJurnalRequest(DetailTitipJurnalParams params) {
        this.params = params;
    }
}
