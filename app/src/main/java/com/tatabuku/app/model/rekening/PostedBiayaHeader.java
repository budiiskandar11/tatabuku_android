package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostedBiayaHeader {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("voucher_no")
    @Expose
    private String voucherNo;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("bank_id")
    @Expose
    private Integer bankId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("voucher_type")
    @Expose
    private String voucher_type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVoucher_type() {
        return voucher_type;
    }

    public void setVoucher_type(String voucher_type) {
        this.voucher_type = voucher_type;
    }
}