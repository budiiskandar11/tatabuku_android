package com.tatabuku.app.model.penjualan;

public class CustomerDetailPostParam {
    private Integer partner_id;
    private String filter;
    private String state;
    private String sort;

    public CustomerDetailPostParam(Integer partner_id, String filter, String state, String sort) {
        this.partner_id = partner_id;
        this.filter = filter;
        this.state = state;
        this.sort = sort;
    }
}
