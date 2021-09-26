package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopTenProduct {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_sold")
    @Expose
    private Double productSold;
    @SerializedName("percentage_qty")
    @Expose
    private Double percentageQty;
    @SerializedName("amount_sold")
    @Expose
    private Double amountSold;
    @SerializedName("percentage_amount")
    @Expose
    private Double percentageAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductSold() {
        return productSold;
    }

    public void setProductSold(Double productSold) {
        this.productSold = productSold;
    }

    public Double getPercentageQty() {
        return percentageQty;
    }

    public void setPercentageQty(Double percentageQty) {
        this.percentageQty = percentageQty;
    }

    public Double getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(Double amountSold) {
        this.amountSold = amountSold;
    }

    public Double getPercentageAmount() {
        return percentageAmount;
    }

    public void setPercentageAmount(Double percentageAmount) {
        this.percentageAmount = percentageAmount;
    }

}