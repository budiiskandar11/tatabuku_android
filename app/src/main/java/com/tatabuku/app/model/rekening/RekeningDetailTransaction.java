package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RekeningDetailTransaction {

    @SerializedName("voucher_no")
    @Expose
    private String voucherNo;
    @SerializedName("partner_id")
    @Expose
    private String partnerId;
    @SerializedName("partner_id_id")
    @Expose
    private String partnerIdId;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("link")
    @Expose
    private RekeningDetailLink link;

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerIdId() {
        return partnerIdId;
    }

    public void setPartnerIdId(String partnerIdId) {
        this.partnerIdId = partnerIdId;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public RekeningDetailLink getLink() {
        return link;
    }

    public void setLink(RekeningDetailLink link) {
        this.link = link;
    }

}
