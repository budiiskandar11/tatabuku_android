package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerDetailDPData {

    @SerializedName("datalist")
    @Expose
    private List<DPData> datalist = null;

    public List<DPData> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DPData> datalist) {
        this.datalist = datalist;
    }

}