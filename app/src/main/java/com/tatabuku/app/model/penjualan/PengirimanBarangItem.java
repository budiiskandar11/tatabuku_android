package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengirimanBarangItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("qty_order")
    @Expose
    private Double qtyOrder = 0.0;
    @SerializedName("product_uom")
    @Expose
    private String productUom;
    @SerializedName("qty_deliver")
    @Expose
    private Double qtyDeliver = 0.0;
    @SerializedName("price_unit")
    @Expose
    private Double priceUnit = 0.0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getQtyOrder() {
        return qtyOrder;
    }

    public void setQtyOrder(Double qtyOrder) {
        this.qtyOrder = qtyOrder;
    }

    public String getProductUom() {
        return productUom;
    }

    public void setProductUom(String productUom) {
        this.productUom = productUom;
    }

    public Double getQtyDeliver() {
        return qtyDeliver;
    }

    public void setQtyDeliver(Double qtyDeliver) {
        this.qtyDeliver = qtyDeliver;
    }
    public Double getDiffQty() {
        return qtyOrder - qtyDeliver;
    }

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }
}