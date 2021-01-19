package com.tatabuku.app.model.pembelian;

public class ReturnModel {

    private Integer product_id;
    private Integer qty = 0;

    public ReturnModel(OrderModel item) {
        this.product_id = item.getProduct_id();
        this.qty = item.getProduct_qty().intValue();
    }

    public ReturnModel(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}