package com.tatabuku.app.model.pembelian;

public class RekeningDataModel {
    private String jsonrpc = "2.0";
    private RekeningDataParams params;

    public RekeningDataModel(int id) {
        params = new RekeningDataParams(id);
    }
}
