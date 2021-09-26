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
    "vat": "4329738947028",
    "status_pajak": "pkp",
    "property_supplier_payment_term_id": 2}}}
 */
public class AddSupplierModel {
    private String jsonrpc = "2.0";
    private AddSupplierParams params;

    public AddSupplierParams getParams() {
        return params;
    }

    public void setParams(AddSupplierParams params) {
        this.params = params;
    }

    public AddSupplierModel(String name, String street, String city, String city_id, String state_id, String email, String mobile, Double debit_limit, Integer property_supplier_payment_term_id, String vat, String status_pajak) {
        params = new AddSupplierParams(new AddSupplierData(name, street, city, city_id, state_id, email, mobile, debit_limit, property_supplier_payment_term_id, vat, status_pajak));
    }
}
