package com.tatabuku.app.model.pembelian;

public class LoadPaymentModel {
    private String jsonrpc = "2.0";
    private LoadPaymentParams params;

    public LoadPaymentModel(int partner_id, String type, String filter) {
        this.params = new LoadPaymentParams(partner_id, type, filter);
    }
}
