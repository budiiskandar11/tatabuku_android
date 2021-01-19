package com.tatabuku.app.model.penjualan;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailPenjualanResult implements Parcelable {

    @SerializedName("order_no")
    @Expose
    private String orderNo;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("partner_shipping_addrs")
    @Expose
    private PartnerShippingAddrs partnerShippingAddrs;
    @SerializedName("partner_contact")
    @Expose
    private PartnerContact partnerContact;
    @SerializedName("order_line")
    @Expose
    private List<PenjualanOrderLine> orderLine = null;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public PartnerShippingAddrs getPartnerShippingAddrs() {
        return partnerShippingAddrs;
    }

    public void setPartnerShippingAddrs(PartnerShippingAddrs partnerShippingAddrs) {
        this.partnerShippingAddrs = partnerShippingAddrs;
    }

    public PartnerContact getPartnerContact() {
        return partnerContact;
    }

    public void setPartnerContact(PartnerContact partnerContact) {
        this.partnerContact = partnerContact;
    }

    public List<PenjualanOrderLine> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<PenjualanOrderLine> orderLine) {
        this.orderLine = orderLine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderNo);
        dest.writeString(this.orderDate);
    }

    public OrderDetailPenjualanResult() {
    }

    protected OrderDetailPenjualanResult(Parcel in) {
        this.orderNo = in.readString();
        this.orderDate = in.readString();
    }

    public static final Parcelable.Creator<OrderDetailPenjualanResult> CREATOR = new Parcelable.Creator<OrderDetailPenjualanResult>() {
        @Override
        public OrderDetailPenjualanResult createFromParcel(Parcel source) {
            return new OrderDetailPenjualanResult(source);
        }

        @Override
        public OrderDetailPenjualanResult[] newArray(int size) {
            return new OrderDetailPenjualanResult[size];
        }
    };
}