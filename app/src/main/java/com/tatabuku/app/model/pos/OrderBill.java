package com.tatabuku.app.model.pos;

public class OrderBill {

    private Integer product_id;
    private String name;
    private String image;
    private Integer product_qty;
    private Double product_price;

    public OrderBill(Integer product_id,String name,String image,Integer product_qty, Double product_price){
        this.product_id = product_id;
        this.name = name;
        this.image = image;
        this.product_qty = product_qty;
        this.product_price = product_price;
    }


    public Integer getId() {
        return product_id;
    }

    public void setId(Integer product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(Integer product_qty) {
        this.product_qty = product_qty;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public void setProduct_qty(Double product_price) {
        this.product_price = product_price;
    }

}
