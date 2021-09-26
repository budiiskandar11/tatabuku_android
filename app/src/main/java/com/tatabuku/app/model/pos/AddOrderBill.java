package com.tatabuku.app.model.pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.model.pembelian.OrderDetailItem;
import com.tatabuku.app.model.pembelian.SupplierResult;

import java.util.ArrayList;
import java.util.List;

public class AddOrderBill {

    @SerializedName("costumer")
    @Expose
    private SupplierResult costumer;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("orderBill")
    @Expose
    private ArrayList<OrderBill> orderBill = null;

    public AddOrderBill(SupplierResult costumer, ArrayList<OrderBill> orderBil, Double total ){
        this.costumer = costumer;
        this.orderBill = orderBil;
        this.total = total;
    }

    public SupplierResult getPartnerId() {
        return costumer;
    }

    public void setPartnerId(SupplierResult costumer) {
        this.costumer = costumer;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ArrayList<OrderBill> getOrderBill() {
        return orderBill;
    }

    public void setOrderBill(ArrayList<OrderBill> orderBill) {
        this.orderBill = orderBill;
    }

}
