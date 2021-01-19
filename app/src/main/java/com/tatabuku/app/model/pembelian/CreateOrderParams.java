package com.tatabuku.app.model.pembelian;

public class CreateOrderParams {
    private OrderModel order_line[];
    private Integer partner_id;

    public CreateOrderParams(OrderModel[] order_line, Integer partner_id) {
        this.order_line = order_line;
        this.partner_id = partner_id;
    }
}
