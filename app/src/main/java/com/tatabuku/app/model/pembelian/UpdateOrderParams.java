package com.tatabuku.app.model.pembelian;

public class UpdateOrderParams {
    private OrderModel order_line[];
    private Integer id;
    private Integer sale_id;

    public UpdateOrderParams(OrderModel[] order_line, Integer id) {
        this.order_line = order_line;
        this.id = id;
        this.sale_id = id;
    }
}
