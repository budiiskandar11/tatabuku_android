package com.tatabuku.app.model.penjualan;

public class DPPostParam {
    private Integer id;
    private Integer partner_id;
    private Integer amount;
    private String date;
    private Integer bank_id;

    public DPPostParam(Integer id, Integer partner_id, Integer amount, String date, Integer bank_id) {
        this.id = id;
        this.partner_id = partner_id;
        this.amount = amount;
        this.date = date;
        this.bank_id = bank_id;
    }
}
