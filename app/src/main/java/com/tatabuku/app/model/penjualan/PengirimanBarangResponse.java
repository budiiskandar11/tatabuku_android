package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengirimanBarangResponse {

    @SerializedName("result")
    @Expose
    private PengirimanBarangResult result;

    public PengirimanBarangResult getResult() {
        return result;
    }

    public void setResult(PengirimanBarangResult result) {
        this.result = result;
    }

}