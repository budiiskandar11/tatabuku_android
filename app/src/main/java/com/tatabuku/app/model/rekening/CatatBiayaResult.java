package com.tatabuku.app.model.rekening;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatatBiayaResult {

    @SerializedName("header_bank_data")
    @Expose
    private CatatBiayaHeader headerBankData;
    @SerializedName("list_biaya")
    @Expose
    private List<CatatBiayaList> listBiaya = null;

    public CatatBiayaHeader getHeaderBankData() {
        return headerBankData;
    }

    public void setHeaderBankData(CatatBiayaHeader headerBankData) {
        this.headerBankData = headerBankData;
    }

    public List<CatatBiayaList> getListBiaya() {
        return listBiaya;
    }

    public void setListBiaya(List<CatatBiayaList> listBiaya) {
        this.listBiaya = listBiaya;
    }

}