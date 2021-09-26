package com.tatabuku.app.model.pembelian;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailResult implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_order")
    @Expose
    private String dateOrder;
    @SerializedName("dest_address_id")
    @Expose
    private WarehouseAddress destAddressId;
    @SerializedName("partner_id")
    @Expose
    private SupplierResult partnerId;
    @SerializedName("order_line")
    @Expose
    private List<OrderDetailItem> orderLine = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public WarehouseAddress getDestAddressId() {
        return destAddressId;
    }

    public void setDestAddressId(WarehouseAddress destAddressId) {
        this.destAddressId = destAddressId;
    }

    public SupplierResult getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(SupplierResult partnerId) {
        this.partnerId = partnerId;
    }

    public List<OrderDetailItem> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<OrderDetailItem> orderLine) {
        this.orderLine = orderLine;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.dateOrder);
    }

    public OrderDetailResult() {
    }

    protected OrderDetailResult(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.dateOrder = in.readString();
    }

    public static final Parcelable.Creator<OrderDetailResult> CREATOR = new Parcelable.Creator<OrderDetailResult>() {
        @Override
        public OrderDetailResult createFromParcel(Parcel source) {
            return new OrderDetailResult(source);
        }

        @Override
        public OrderDetailResult[] newArray(int size) {
            return new OrderDetailResult[size];
        }
    };
}