package com.tatabuku.app.model.pembelian;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceData {

    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("jumlah_product")
    @Expose
    private Integer jumlahProduct;
    @SerializedName("invoice_line_details")
    @Expose
    private List<InvoiceLineDetail> invoiceLineDetails = null;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;
    @SerializedName("tax")
    @Expose
    private Double tax;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("amount_already_paid")
    @Expose
    private Double amountAlreadyPaid = 0.0;
    @SerializedName("amount_to_paid")
    @Expose
    private Double amountToPaid = 0.0;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getJumlahProduct() {
        return jumlahProduct;
    }

    public void setJumlahProduct(Integer jumlahProduct) {
        this.jumlahProduct = jumlahProduct;
    }

    public List<InvoiceLineDetail> getInvoiceLineDetails() {
        return invoiceLineDetails;
    }

    public void setInvoiceLineDetails(List<InvoiceLineDetail> invoiceLineDetails) {
        this.invoiceLineDetails = invoiceLineDetails;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAmountAlreadyPaid() {
        return amountAlreadyPaid;
    }

    public void setAmountAlreadyPaid(Double amountAlreadyPaid) {
        this.amountAlreadyPaid = amountAlreadyPaid;
    }

    public Double getAmountToPaid() {
        return amountToPaid;
    }

    public void setAmountToPaid(Double amountToPaid) {
        this.amountToPaid = amountToPaid;
    }
}