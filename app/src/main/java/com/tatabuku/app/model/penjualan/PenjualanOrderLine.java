package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PenjualanOrderLine {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_uom_qty")
    @Expose
    private Double productUomQty;
    @SerializedName("product_uom")
    @Expose
    private Integer productUom;
    @SerializedName("price_unit")
    @Expose
    private Double priceUnit;
    @SerializedName("price_total")
    @Expose
    private Double priceTotal;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getProductUomQty() {
        return productUomQty;
    }

    public void setProductUomQty(Double productUomQty) {
        this.productUomQty = productUomQty;
    }

    public Integer getProductUom() {
        return productUom;
    }

    public void setProductUom(Integer productUom) {
        this.productUom = productUom;
    }

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

}