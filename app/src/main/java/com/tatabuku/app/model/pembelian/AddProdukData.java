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
public class AddProdukData {
    private String name;
    private String default_code;
    private String description;
    private String type = "product";
    private Integer standard_price;
    private Integer purchase_price;
    private Integer list_price;
    private Integer x_studio_min_stock;
    private Integer x_studio_max_stock;
    private Integer categ_id;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public AddProdukData(String name, String default_code, String description, Integer sellPrice, Integer buyPrice, Integer x_studio_min_stock, Integer x_studio_max_stock, Integer categ_id) {
        this.name = name;
        this.default_code = default_code;
        this.description = description;
        this.standard_price = buyPrice;
        this.purchase_price = buyPrice;
        this.list_price = sellPrice;
        this.x_studio_min_stock = x_studio_min_stock;
        this.x_studio_max_stock = x_studio_max_stock;
        this.categ_id = categ_id;
    }
}
