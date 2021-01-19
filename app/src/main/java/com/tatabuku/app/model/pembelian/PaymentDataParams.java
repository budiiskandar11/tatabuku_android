package com.tatabuku.app.model.pembelian;

import com.tatabuku.app.util.StringHelper;

public class PaymentDataParams {
    private Integer invoice_id;

    public PaymentDataParams(Integer invoice_id) {
        this.invoice_id = invoice_id;
    }
}
