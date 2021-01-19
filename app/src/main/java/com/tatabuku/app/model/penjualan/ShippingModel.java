package com.tatabuku.app.model.penjualan;

import com.tatabuku.app.model.pembelian.PenerimaanBarangItem;

public class ShippingModel {
    private Integer line_id;
    private Integer product_id;
    private Integer quantity_done = 0;

    public ShippingModel(PengirimanBarangItem item) {
        this.line_id = item.getId();
        this.product_id = item.getProductId();
        this.quantity_done = item.getQtyDeliver().intValue();
    }
}
