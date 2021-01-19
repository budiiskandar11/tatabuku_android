
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderLine {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_uom_qty")
    @Expose
    private Double productUomQty;
    @SerializedName("price_unit")
    @Expose
    private Double priceUnit;
    @SerializedName("price_subtotal")
    @Expose
    private Double priceSubtotal;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getProductUomQty() {
        return productUomQty;
    }

    public void setProductUomQty(Double productUomQty) {
        this.productUomQty = productUomQty;
    }

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Double getPriceSubtotal() {
        return priceSubtotal;
    }

    public void setPriceSubtotal(Double priceSubtotal) {
        this.priceSubtotal = priceSubtotal;
    }

}