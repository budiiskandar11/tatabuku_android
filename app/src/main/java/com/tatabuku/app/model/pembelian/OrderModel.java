package com.tatabuku.app.model.pembelian;

import android.os.Parcel;
import android.os.Parcelable;

import com.tatabuku.app.model.penjualan.PenjualanOrderLine;

import java.util.Objects;

public class OrderModel implements Parcelable {

    private Integer product_id;
    private Integer product_qty = 0;
    private Double price_unit = 0.0;
    private String name = "";
    private String image = "";

    public OrderModel(OrderDetailItem item) {
        this.product_id = item.getProductId().getProductTmplId();
        this.product_qty = item.getProductQty().intValue();
        this.price_unit = item.getPriceUnit();
        this.name = item.getProductId().getName();
        this.image = item.getProductId().getImage();
    }

    public OrderModel(PenjualanOrderLine line) {
        this.product_id = line.getProductId();
        this.product_qty = line.getProductUomQty().intValue();
        this.price_unit = line.getPriceUnit();
        this.name = line.getName();
    }

    public OrderModel(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getProduct_qty() {
        return product_qty;
    }

    public void setProduct_qty(Integer product_qty) {
        this.product_qty = product_qty;
    }

    public Double getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(Double price_unit) {
        this.price_unit = price_unit;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel that = (OrderModel) o;
        return Objects.equals(product_id, that.product_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.product_id);
        dest.writeValue(this.product_qty);
        dest.writeValue(this.price_unit);
        dest.writeString(this.name);
        dest.writeString(this.image);
    }

    protected OrderModel(Parcel in) {
        this.product_id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.product_qty = (Integer) in.readValue(Integer.class.getClassLoader());
        this.price_unit = (Double) in.readValue(Double.class.getClassLoader());
        this.name = in.readString();
        this.image = in.readString();
    }

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel source) {
            return new OrderModel(source);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };
}