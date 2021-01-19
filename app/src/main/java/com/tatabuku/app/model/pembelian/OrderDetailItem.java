package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailItem {

    @SerializedName("product_id")
    @Expose
    private Produk productId;
    @SerializedName("price_unit")
    @Expose
    private Double priceUnit;
    @SerializedName("product_qty")
    @Expose
    private Double productQty;
    @SerializedName("price_subtotal")
    @Expose
    private Double priceSubtotal;

    public Produk getProductId() {
        return productId;
    }

    public void setProductId(Produk productId) {
        this.productId = productId;
    }

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Double getProductQty() {
        return productQty;
    }

    public void setProductQty(Double productQty) {
        this.productQty = productQty;
    }

    public Double getPriceSubtotal() {
        return priceSubtotal;
    }

    public void setPriceSubtotal(Double priceSubtotal) {
        this.priceSubtotal = priceSubtotal;
    }

}