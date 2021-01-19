package com.tatabuku.app.model.penjualan;

import com.tatabuku.app.model.pembelian.SubmitReceivedParams;

public class ShippingReceivedModel {
    private String jsonrpc = "2.0";
    private ShippingReceivedParams params;

    public ShippingReceivedModel(ShippingReceivedParams params) {
        this.params = params;
    }
}
