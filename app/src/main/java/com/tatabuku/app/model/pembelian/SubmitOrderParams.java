package com.tatabuku.app.model.pembelian;

public class SubmitOrderParams {
    private Integer id;
    private Integer sale_id;

    public SubmitOrderParams(Integer id) {
        this.id = id;
        this.sale_id = id;
    }
}
