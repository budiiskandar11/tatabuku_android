package com.tatabuku.app.model.pembelian;


/*
{"jsonrpc": "2.0", "params": {"partner_id":23,
                                "amount":30000,
                                "date": "2020-09-21",
                                "bank_id": 7
                                }
}
 */
public class AddDPParams {
    private Integer partner_id;
    private Integer amount;
    private String date;
    private Integer bank_id;

    public AddDPParams(Integer partner_id, Integer amount, String date, Integer bank_id) {
        this.partner_id = partner_id;
        this.amount = amount;
        this.date = date;
        this.bank_id = bank_id;
    }
}
