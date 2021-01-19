package com.tatabuku.app.model.penjualan;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PenjualanData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("partner_id")
    @Expose
    private Integer partnerId;
    @SerializedName("partner_name")
    @Expose
    private String partnerName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;
    @SerializedName("pajak")
    @Expose
    private Double pajak;
    @SerializedName("amount_total")
    @Expose
    private Double amountTotal;
    @SerializedName("order_line")
    @Expose
    private List<CustomerDetailOrderLine> orderLine = null;
    @SerializedName("jumlah_barang")
    @Expose
    private Integer jumlahBarang;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("state")
    @Expose
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getPajak() {
        return pajak;
    }

    public void setPajak(Double pajak) {
        this.pajak = pajak;
    }

    public Double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public List<CustomerDetailOrderLine> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<CustomerDetailOrderLine> orderLine) {
        this.orderLine = orderLine;
    }

    public Integer getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(Integer jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
