package com.tatabuku.app.model.penjualan;

public class PartnerIdPostModel {
    private String jsonrpc = "2.0";
    private PartnerIdPostParam params;

    public PartnerIdPostModel(Integer id) {
        this.params = new PartnerIdPostParam(id);
    }
}
