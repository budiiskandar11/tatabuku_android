package com.tatabuku.app.model.pembelian;

public class ProductIdPostModel {
    private String jsonrpc = "2.0";
    private ProductIdPostParam params;

    public ProductIdPostModel(Integer id) {
        this.params = new ProductIdPostParam(id);
    }
}
