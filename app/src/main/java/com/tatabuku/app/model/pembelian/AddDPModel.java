package com.tatabuku.app.model.pembelian;


/*
{"jsonrpc": "2.0", "params": {"partner_id":23,
                                "amount":30000,
                                "date": "2020-09-21",
                                "bank_id": 7
                                }
}
 */
public class AddDPModel {
    private String jsonrpc = "2.0";
    private AddDPParams params;

    public AddDPModel(Integer partner_id, Integer amount, String date, Integer bank_id) {
        params = new AddDPParams(partner_id, amount, date, bank_id);
    }
}
