package com.tatabuku.app.model.titip_jurnal;

public class CreateTitipJurnalRequest {
    private String jsonrpc = "2.0";
    private CreateTitipJurnalParams params;

    public CreateTitipJurnalRequest(CreateTitipJurnalParams params) {
        this.params = params;
    }
}
