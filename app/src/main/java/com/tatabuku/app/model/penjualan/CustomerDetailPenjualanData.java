package com.tatabuku.app.model.penjualan;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailPenjualanData {

    @SerializedName("datalist")
    @Expose
    private List<PenjualanData> datalist = null;

    public List<PenjualanData> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<PenjualanData> datalist) {
        this.datalist = datalist;
    }

}