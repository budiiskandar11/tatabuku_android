package com.tatabuku.app.model.pembelian;

/*
{"jsonrpc": "2.0", "params": {"partner_id":23,
                                "date" : "2020-09-22",
                                "move_lines":[{"product_id":1,
                                                "qty":1},
                                                {"product_id":3,
                                                "qty":1}
                                                ]
                                }
}
 */
public class ReturnOrderModel {
    private String jsonrpc = "2.0";
    private ReturnOrderParams params;

    public ReturnOrderModel(ReturnOrderParams params) {
        this.params = params;
    }
}
