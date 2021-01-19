package com.tatabuku.app.model.rekening;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RekeningDetailResult {

    @SerializedName("Header_data")
    @Expose
    private RekeningDetailHeader headerData;
    @SerializedName("List_transaksi")
    @Expose
    private List<RekeningDetailTransaction> listTransaksi = null;
    @SerializedName("message")
    @Expose
    private String message;

    public RekeningDetailHeader getHeaderData() {
        return headerData;
    }

    public void setHeaderData(RekeningDetailHeader headerData) {
        this.headerData = headerData;
    }

    public List<RekeningDetailTransaction> getListTransaksi() {
        return listTransaksi;
    }

    public void setListTransaksi(List<RekeningDetailTransaction> listTransaksi) {
        this.listTransaksi = listTransaksi;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}


