package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadProductResult {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product_data")
    @Expose
    private LoadProductData productData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoadProductData getProductData() {
        return productData;
    }

    public void setProductData(LoadProductData productData) {
        this.productData = productData;
    }

}