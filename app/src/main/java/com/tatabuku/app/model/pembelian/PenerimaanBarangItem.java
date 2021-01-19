package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PenerimaanBarangItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_uom_qty")
    @Expose
    private Double productUomQty;
    @SerializedName("product_uom")
    @Expose
    private String productUom;
    @SerializedName("price_unit")
    @Expose
    private Double priceUnit;
    @SerializedName("quantity_done")
    @Expose
    private Double quantityDone;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getProductUomQty() {
        return productUomQty;
    }

    public void setProductUomQty(Double productUomQty) {
        this.productUomQty = productUomQty;
    }

    public String getProductUom() {
        return productUom;
    }

    public void setProductUom(String productUom) {
        this.productUom = productUom;
    }

    public Double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Double getQuantityDone() {
        return quantityDone;
    }

    public void setQuantityDone(Double quantityDone) {
        this.quantityDone = quantityDone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getDiffQty() {
        return productUomQty - quantityDone;
    }
}