package com.tatabuku.app.model.pembelian;


/*
{"jsonrpc": "2.0", "params": {"data":{
    "name": "T",
    "default_code": "12345",
    "description": "deskripsi",
    "type": "product",
    "x_studio_min_stock":10,
    "x_studio_max_stock": 10}}}
 */
public class AddProdukParams {
    private AddProdukData data;

    public AddProdukData getData() {
        return data;
    }

    public void setData(AddProdukData data) {
        this.data = data;
    }

    public AddProdukParams(AddProdukData data) {
        this.data = data;
    }
}
