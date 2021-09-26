package com.tatabuku.app.model.rekening;

public class CreateBankAccountRequest {
    private String jsonrpc = "2.0";
    private CreateBankAccountParams params;

    public CreateBankAccountRequest(CreateBankAccountParams params) {
        this.params = params;
    }
}