package com.tatabuku.app.model.penjualan;

public class CustomerDetailPostModel {
    private String jsonrpc = "2.0";
    private CustomerDetailPostParam params;

    public CustomerDetailPostModel(Integer partner_id, String filter, String state, String sort) {
        this.params = new CustomerDetailPostParam(partner_id, filter, state, sort);
    }

    public CustomerDetailPostModel(Integer partner_id, String filter, String sort) {
        this.params = new CustomerDetailPostParam(partner_id, filter, null, sort);
    }
}
