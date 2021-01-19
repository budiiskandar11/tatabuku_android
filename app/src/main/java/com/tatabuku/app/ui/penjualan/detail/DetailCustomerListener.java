package com.tatabuku.app.ui.penjualan.detail;

public interface DetailCustomerListener {
    void onEditDp(Integer id);

    void onCheckout(Integer id);

    void onKirimBarang(Integer id);

    void onViewOrder(Integer id);

    void onBayarHutang(Integer id, Boolean isEdit);

    void onViewPayment(Integer id);
}
