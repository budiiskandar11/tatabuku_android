package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceLineDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("qty")
    @Expose
    private Double qty;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("price_total")
    @Expose
    private Double priceTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public Double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }

}