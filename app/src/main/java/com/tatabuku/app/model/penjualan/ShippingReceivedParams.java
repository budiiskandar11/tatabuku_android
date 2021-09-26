package com.tatabuku.app.model.penjualan;

import com.tatabuku.app.model.pembelian.ReceivedModel;

public class ShippingReceivedParams {
    private ShippingModel list_barang[];
    private Integer picking_id;

    public ShippingReceivedParams(ShippingModel[] list_barang, Integer picking_id) {
        this.list_barang = list_barang;
        this.picking_id = picking_id;
    }
}
