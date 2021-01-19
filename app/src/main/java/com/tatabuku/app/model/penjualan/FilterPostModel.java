package com.tatabuku.app.model.penjualan;

public class FilterPostModel {
    private String jsonrpc = "2.0";
    private FilterPostParam params;

    public FilterPostModel(String filter, String sort) {
        this.params = new FilterPostParam(filter, sort);
    }
}
