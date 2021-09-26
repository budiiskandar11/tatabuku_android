package com.tatabuku.app.model.pembelian;

public class EditProdukModel {
    private String jsonrpc = "2.0";
    private EditProdukParam params;

    public EditProdukModel(EditProdukParam params) {
        this.params = params;
    }
}
