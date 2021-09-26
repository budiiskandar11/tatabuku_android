package com.tatabuku.app.ui.pos.listener;

import android.content.DialogInterface;

import com.tatabuku.app.model.pembelian.Category;
import com.tatabuku.app.model.pos.OrderBill;

public interface PosListener {
    void onOrderChanged();

    void onEditProduct(int productId);

    void onSelectCategory(Category category);

    void onPaymentResultCostumer(String message);

    void onPaymentResultNotCostumer(String message, Integer id);

    void onEditOrder(OrderBill orderBill);
}
