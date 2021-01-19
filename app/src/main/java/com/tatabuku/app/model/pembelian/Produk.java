package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Produk {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("default_code")
    @Expose
    private String defaultCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("categ_id")
    @Expose
    private Category categId;
    @SerializedName("standard_price")
    @Expose
    private Double standardPrice;
    @SerializedName("qty_available")
    @Expose
    private Double qtyAvailable;
    @SerializedName("product_tmpl_id")
    @Expose
    private Integer productTmplId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDefaultCode() {
        return defaultCode;
    }

    public void setDefaultCode(String defaultCode) {
        this.defaultCode = defaultCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategId() {
        return categId;
    }

    public void setCategId(Category categId) {
        this.categId = categId;
    }

    public Double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Double standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Double getQtyAvailable() {
        return qtyAvailable;
    }

    public void setQtyAvailable(Double qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getProductTmplId() {
        return productTmplId;
    }

    public void setProductTmplId(Integer productTmplId) {
        this.productTmplId = productTmplId;
    }
}