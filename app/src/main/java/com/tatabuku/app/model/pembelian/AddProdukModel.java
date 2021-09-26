package com.tatabuku.app.model.pembelian;

/*
{"jsonrpc": "2.0", "params": {"data":{
    "name": "T",
    "default_code": "12345",
    "description": "deskripsi",
    "type": "product",
    "standard_price": 1000,
    "image": ,
    "categ_id": 1,
    "x_studio_min_stock":10,
    "x_studio_max_stock": 10}}}
 */
public class AddProdukModel {
    private String jsonrpc = "2.0";
    private AddProdukParams params;

    public AddProdukParams getParams() {
        return params;
    }

    public void setParams(AddProdukParams params) {
        this.params = params;
    }

    public AddProdukModel(String name, String default_code, String description, Integer sellPrice,Integer buyPrice, Integer x_studio_min_stock, Integer x_studio_max_stock, Integer categ_id) {
        params = new AddProdukParams(new AddProdukData(name, default_code, description, sellPrice, buyPrice, x_studio_min_stock, x_studio_max_stock, categ_id));
    }
}
