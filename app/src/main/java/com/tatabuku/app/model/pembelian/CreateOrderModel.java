package com.tatabuku.app.model.pembelian;

/*
{"jsonrpc": "2.0", "params": {"partner_id":23,
                                "order_line":[{"product_id":1,
                                                "product_qty":3,
                                                "price_unit": 5000}
                                }
}
 */
public class CreateOrderModel {
    private String jsonrpc = "2.0";
    private CreateOrderParams params;

    public CreateOrderModel(CreateOrderParams params) {
        this.params = params;
    }
}
