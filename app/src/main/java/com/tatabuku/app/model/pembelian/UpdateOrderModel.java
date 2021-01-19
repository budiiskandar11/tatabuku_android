package com.tatabuku.app.model.pembelian;

/*
{"jsonrpc": "2.0", "params": {"id":23,
                                "order_line":[{"product_id":1,
                                                "product_qty":3,
                                                "price_unit": 5000}
                                }
}
 */
public class UpdateOrderModel {
    private String jsonrpc = "2.0";
    private UpdateOrderParams params;

    public UpdateOrderModel(UpdateOrderParams params) {
        this.params = params;
    }
}
