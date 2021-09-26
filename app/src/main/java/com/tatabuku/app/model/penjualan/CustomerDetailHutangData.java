package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerDetailHutangData {

    @SerializedName("datalist")
    @Expose
    private List<HutangData> datalist = null;

    public List<HutangData> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<HutangData> datalist) {
        this.datalist = datalist;
    }

}