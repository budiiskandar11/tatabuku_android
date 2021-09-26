package com.tatabuku.app.model.pembelian;


/*
{"jsonrpc": "2.0", "params": {"data":{
    "name": "ujang",
    "street": "Jl. Pemuda",
    "street2":"kota bogor",
    "supplier": "true",
    "email" : "xxx@email.com",
    "mobile" : "080808",
    "debit_limit": 5000000,
    "property_supplier_payment_term_id": 2}}}
 */
public class AddSupplierParams {
    private AddSupplierData data;

    public AddSupplierData getData() {
        return data;
    }

    public void setData(AddSupplierData data) {
        this.data = data;
    }

    public AddSupplierParams(AddSupplierData data) {
        this.data = data;
    }
}
