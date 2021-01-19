package com.tatabuku.app.model.fixed_asset;

/*
{"jsonrpc": "2.0", "params": {"categ_id":1,
                            "name" :"Honda Scoopy",
                            "value": 10000000,
                            "image": "",
                            "date":"2020-10-15",
                            "ref": "PO12342",
                            "supplier_id": 11,
                            "purchase_id": 39,
                            "purchase_line_id":59

                            }}
* */

public class TambahAssetParams {
    private int categ_id;
    private String name;
    private double value;
    private String image;
    private String date;
    private String ref;
    private int supplier_id;
    private int purchase_id;
    private int purchase_line_id;
    private int method_number;
    private int qty;

    public TambahAssetParams(int categ_id, String name, double value, String image, String date, String ref, int supplier_id, int purchase_id, int purchase_line_id) {
        this.categ_id = categ_id;
        this.name = name;
        this.value = value;
        this.image = image;
        this.date = date;
        this.ref = ref;
        this.supplier_id = supplier_id;
        this.purchase_id = purchase_id;
        this.purchase_line_id = purchase_line_id;
    }

    public TambahAssetParams(int categ_id, String name, double value, String date) {
        this.categ_id = categ_id;
        this.name = name;
        this.value = value;
        this.date = date;
    }

    public int getCateg_id() {
        return categ_id;
    }

    public void setCateg_id(int categ_id) {
        this.categ_id = categ_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    public int getPurchase_line_id() {
        return purchase_line_id;
    }

    public void setPurchase_line_id(int purchase_line_id) {
        this.purchase_line_id = purchase_line_id;
    }

    public int getMethod_number() {
        return method_number;
    }

    public void setMethod_number(int method_number) {
        this.method_number = method_number;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
