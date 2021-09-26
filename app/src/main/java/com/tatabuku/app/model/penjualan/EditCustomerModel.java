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
public class EditCustomerModel {
    private String jsonrpc = "2.0";
    private EditCustomerParam params;

    public EditCustomerParam getParams() {
        return params;
    }

    public void setParams(EditCustomerParam params) {
        this.params = params;
    }

    public EditCustomerModel(EditCustomerParam params) {
        this.params = params;
    }
}
