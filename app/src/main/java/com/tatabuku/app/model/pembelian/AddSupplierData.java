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
public class AddSupplierData {
    private String name;
    private String street;
    private String city;
    private String city_id;
    private String state_id;
    private String supplier = "true";
    private String email;
    private String mobile;
    private Double debit_limit;
    private Integer property_supplier_payment_term_id;
    private String vat;
    private String status_pajak;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public AddSupplierData(String name, String street, String city, String city_id, String state_id, String email, String mobile, Double debit_limit, Integer property_supplier_payment_term_id, String vat, String status_pajak) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.city_id = city_id;
        this.state_id = state_id;
        this.email = email;
        this.mobile = mobile;
        this.debit_limit = debit_limit;
        this.property_supplier_payment_term_id = property_supplier_payment_term_id;
        this.vat = vat;
        this.status_pajak = status_pajak;
    }
}
