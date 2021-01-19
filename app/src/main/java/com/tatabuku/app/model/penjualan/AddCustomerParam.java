package com.tatabuku.app.model.penjualan;


/*
{"jsonrpc": "2.0", "params": {
    "name": "Trully Cafse",
    "street": "Jl. Pemuda",
    "street2":"kota bogor",
    "supplier": "true",
    "email" : "xxx@email.com",
    "mobile" : "080808",
    "credit_limit": 5000000,
    "vat": "4329738947028",
    "status_pajak": "pkp",
    "property_payment_term_id": 2,
    "subdistrict_id":7431,
    "city_id" : 455,
    "state_id": 721,
    "zip":"43359"
    }}
 */
public class AddCustomerParam {
    private String name;
    private String street;
    private String street2;
    private String email;
    private String mobile;
    private Double credit_limit;
    private Integer property_payment_term_id;
    private String vat;
    private String status_pajak;
    private String state_id;
    private String city_id;
    private String subdistrict_id;
    private String zip;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public AddCustomerParam(String name, String street, String street2, String email, String mobile, Double credit_limit, Integer property_payment_term_id, String vat, String status_pajak, String state_id, String city_id, String subdistrict_id, String zip) {
        this.name = name;
        this.street = street;
        this.street2 = street2;
        this.email = email;
        this.mobile = mobile;
        this.credit_limit = credit_limit;
        this.property_payment_term_id = property_payment_term_id;
        this.vat = vat;
        this.status_pajak = status_pajak;
        this.state_id = state_id;
        this.city_id = city_id;
        this.subdistrict_id = subdistrict_id;
        this.zip = zip;
    }
}
