package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HutangData {

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
    @SerializedName("inv no")
    @Expose
    private String invNo;
    @SerializedName("jumlah_barang")
    @Expose
    private Integer jumlahBarang;
    @SerializedName("order_line")
    @Expose
    private List<CustomerDetailOrderLine> orderLine = null;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;
    @SerializedName("PPN")
    @Expose
    private Double ppn;
    @SerializedName("total_invoice")
    @Expose
    private Double totalInvoice;
    @SerializedName("total_harus_dibayar")
    @Expose
    private Double totalHarusDibayar;
    @SerializedName("status")
    @Expose
    private String status;

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

    public String getInvNo() {
        return invNo;
    }

    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    public Integer getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(Integer jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public List<CustomerDetailOrderLine> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<CustomerDetailOrderLine> orderLine) {
        this.orderLine = orderLine;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getPPN() {
        return ppn;
    }

    public void setPPN(Double ppn) {
        this.ppn = ppn;
    }

    public Double getTotalInvoice() {
        return totalInvoice;
    }

    public void setTotalInvoice(Double totalInvoice) {
        this.totalInvoice = totalInvoice;
    }

    public Double getTotalHarusDibayar() {
        return totalHarusDibayar;
    }

    public void setTotalHarusDibayar(Double totalHarusDibayar) {
        this.totalHarusDibayar = totalHarusDibayar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
