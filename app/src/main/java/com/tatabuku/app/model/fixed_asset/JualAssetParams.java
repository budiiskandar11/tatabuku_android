package com.tatabuku.app.model.fixed_asset;

/*
{"jsonrpc": "2.0", "params": {"asset_id":13,
                                "customer_name": "Angelina Baba",
                                "date": "2020-11-11",
                                "price" : "90000000"

                            }}
*/

public class JualAssetParams {
    private int asset_id;
    private String customer_name;
    private String date;
    private String price;

    public JualAssetParams(int asset_id, String customer_name, String date, String price) {
        this.asset_id = asset_id;
        this.customer_name = customer_name;
        this.date = date;
        this.price = price;
    }
}
