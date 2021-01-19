package com.tatabuku.app.model.pembelian;

public class LoadPaymentParams {
    private int partner_id;
    private String type;
    private String filter;

    public LoadPaymentParams(int partner_id, String type, String filter) {
        this.partner_id = partner_id;
        this.type = type;
        this.filter = filter;
    }
}
