package com.tatabuku.app.model.pembelian;

public class ReceivedModel {

    private Integer id;
    private Integer product_id;
    private Integer quantity_done = 0;

    public ReceivedModel(PenerimaanBarangItem item) {
        this.id = item.getId();
        this.product_id = item.getProductId();
        this.quantity_done = item.getQuantityDone().intValue();
    }
}