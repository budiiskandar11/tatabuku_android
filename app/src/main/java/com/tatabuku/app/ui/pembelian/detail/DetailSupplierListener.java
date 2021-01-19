package com.tatabuku.app.ui.pembelian.detail;

public interface DetailSupplierListener {

    void onEditDP(Integer id);

    void onCheckout(Integer id);

    void onTerimaBarang(Integer id);

    void onViewOrder(Integer id);

    void onBayarHutang(Integer id, Boolean isEdit);

    void onViewPayment(Integer id);
}
