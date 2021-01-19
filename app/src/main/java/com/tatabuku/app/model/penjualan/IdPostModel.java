package com.tatabuku.app.model.penjualan;

public class IdPostModel {
    private String jsonrpc = "2.0";
    private IdPostParam params;

    public IdPostModel(Integer id) {
        this.params = new IdPostParam(id);
    }
}
