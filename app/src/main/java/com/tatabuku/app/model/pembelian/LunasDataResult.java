package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LunasDataResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("date_invoice")
    @Expose
    private String dateInvoice;
    @SerializedName("partner_id")
    @Expose
    private SupplierResult partnerId;
    @SerializedName("invoice_line_ids")
    @Expose
    private List<Integer> invoiceLineIds = null;
    @SerializedName("amount_total")
    @Expose
    private Double amountTotal;
    @SerializedName("state")
    @Expose
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateInvoice() {
        return dateInvoice;
    }

    public void setDateInvoice(String dateInvoice) {
        this.dateInvoice = dateInvoice;
    }

    public SupplierResult getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(SupplierResult partnerId) {
        this.partnerId = partnerId;
    }

    public List<Integer> getInvoiceLineIds() {
        return invoiceLineIds;
    }

    public void setInvoiceLineIds(List<Integer> invoiceLineIds) {
        this.invoiceLineIds = invoiceLineIds;
    }

    public Double getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}