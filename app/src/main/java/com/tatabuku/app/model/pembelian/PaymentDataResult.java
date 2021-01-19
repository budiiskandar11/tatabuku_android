package com.tatabuku.app.model.pembelian;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentDataResult {

    @SerializedName("Partner_Data")
    @Expose
    private PartnerData partnerData;
    @SerializedName("Invoice_Data")
    @Expose
    private InvoiceData invoiceData;
    @SerializedName("Bank_Selection")
    @Expose
    private List<RekeningBank> bankSelection = null;
    @SerializedName("Payment_List")
    @Expose
    private List<RekeningBank> paymentList = null;
    @SerializedName("Message")
    @Expose
    private String message;

    public PartnerData getPartnerData() {
        return partnerData;
    }

    public void setPartnerData(PartnerData partnerData) {
        this.partnerData = partnerData;
    }

    public InvoiceData getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
    }

    public List<RekeningBank> getBankSelection() {
        return bankSelection;
    }

    public void setBankSelection(List<RekeningBank> bankSelection) {
        this.bankSelection = bankSelection;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RekeningBank> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<RekeningBank> paymentList) {
        this.paymentList = paymentList;
    }
}