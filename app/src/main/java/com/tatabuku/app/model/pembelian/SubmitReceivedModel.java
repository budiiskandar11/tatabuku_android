package com.tatabuku.app.model.pembelian;

/*
{"jsonrpc": "2.0", "params": {"id":1,
                                "move_lines":[
                {
                    "id": 1,
                    "product_id": {
                        "name": "Product"
                    },
                    "product_uom_qty": 3.0,
                    "quantity_done": 3
                }
            ]}
}
 */
public class SubmitReceivedModel {
    private String jsonrpc = "2.0";
    private SubmitReceivedParams params;

    public SubmitReceivedModel(SubmitReceivedParams params) {
        this.params = params;
    }
}
