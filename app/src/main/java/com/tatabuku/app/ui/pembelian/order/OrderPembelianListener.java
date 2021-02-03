package com.tatabuku.app.ui.pembelian.order;

import com.tatabuku.app.model.pembelian.Category;

public interface OrderPembelianListener {
    void onOrderChanged();

    void onEditProduct(int productId);

    void onSelectCategory(Category category);
}
