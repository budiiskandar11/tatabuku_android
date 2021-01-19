package com.tatabuku.app.model.penjualan;

public class SaleIdPostModel {
    private String jsonrpc = "2.0";
    private SaleIdPostParam params;

    public SaleIdPostModel(Integer sale_id) {
        this.params = new SaleIdPostParam(sale_id);
    }
}
