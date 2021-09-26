package com.tatabuku.app.model.penjualan;

public class DPPostModel {
    private String jsonrpc = "2.0";
    private DPPostParam params;

    public DPPostModel(Integer id, Integer partner_id, Integer amount, String date, Integer bank_id) {
        this.params = new DPPostParam(id, partner_id, amount, date, bank_id);
    }
}
