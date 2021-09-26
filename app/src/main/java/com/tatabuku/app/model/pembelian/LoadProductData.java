package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.model.pembelian.Category;

public class LoadProductData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("default_code")
    @Expose
    private String defaultCode;
    @SerializedName("categ_id")
    @Expose
    private Category categId;
    @SerializedName("list_price")
    @Expose
    private Double listPrice;
    @SerializedName("purchase_price")
    @Expose
    private Double purchasePrice;
    @SerializedName("x_studio_min_stock")
    @Expose
    private Double minStock = 0.0;
    @SerializedName("x_studio_max_stock")
    @Expose
    private Double maxStock = 0.0;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultCode() {
        return defaultCode;
    }

    public void setDefaultCode(String defaultCode) {
        this.defaultCode = defaultCode;
    }

    public Category getCategId() {
        return categId;
    }

    public void setCategId(Category categId) {
        this.categId = categId;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getMinStock() {
        return minStock;
    }

    public void setMinStock(Double minStock) {
        this.minStock = minStock;
    }

    public Double getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Double maxStock) {
        this.maxStock = maxStock;
    }
}