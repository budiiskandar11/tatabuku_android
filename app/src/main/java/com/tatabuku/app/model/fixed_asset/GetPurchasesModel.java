package com.tatabuku.app.model.fixed_asset;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPurchasesModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("partner_id")
    @Expose
    private Integer partnerId;
    @SerializedName("order_line")
    @Expose
    private List<GetPurchasesOrderLine> orderLine = null;

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

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public List<GetPurchasesOrderLine> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<GetPurchasesOrderLine> orderLine) {
        this.orderLine = orderLine;
    }

}