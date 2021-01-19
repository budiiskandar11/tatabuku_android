package com.tatabuku.app.model.penjualan;

import com.tatabuku.app.model.pembelian.AddSupplierData;
import com.tatabuku.app.model.pembelian.AddSupplierParams;

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
public class AddCustomerModel {
    private String jsonrpc = "2.0";
    private AddCustomerParam params;

    public AddCustomerParam getParams() {
        return params;
    }

    public void setParams(AddCustomerParam params) {
        this.params = params;
    }

    public AddCustomerModel(AddCustomerParam params) {
        this.params = params;
    }
}
