package com.tatabuku.app.model.pos;

public class OrderLineModel {
    private Integer product_id;
    private Integer product_qty;

    public OrderLineModel(Integer product_id, Integer product_qty) {
        this.product_id = product_id;
        this.product_qty = product_qty;
    }
}
