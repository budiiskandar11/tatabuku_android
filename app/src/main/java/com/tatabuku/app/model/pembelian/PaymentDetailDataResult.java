package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentDetailDataResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("payment_date")
    @Expose
    private String paymentDate;
    @SerializedName("partner_data")
    @Expose
    private PartnerData partnerData;
    @SerializedName("invoice_data")
    @Expose
    private List<InvoiceData> invoiceData;
    @SerializedName("payment_list")
    @Expose
    private List<RekeningBank> paymentList = null;
    @SerializedName("bank_list")
    @Expose
    private List<RekeningBank> bankList = null;
    @SerializedName("Message")
    @Expose
    private String message;

    public PartnerData getPartnerData() {
        return partnerData;
    }

    public void setPartnerData(PartnerData partnerData) {
        this.partnerData = partnerData;
    }

    public List<InvoiceData> getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(List<InvoiceData> invoiceData) {
        this.invoiceData = invoiceData;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public List<RekeningBank> getBankList() {
        return bankList;
    }

    public void setBankList(List<RekeningBank> bankList) {
        this.bankList = bankList;
    }
}