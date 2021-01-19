package com.tatabuku.app.model.penjualan;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengirimanBarangResult {

    @SerializedName("picking_id")
    @Expose
    private Integer pickingId;
    @SerializedName("Partner_Data")
    @Expose
    private PengirimanBarangPartnerData partnerData;
    @SerializedName("list_barang")
    @Expose
    private List<PengirimanBarangItem> listBarang = null;

    public Integer getPickingId() {
        return pickingId;
    }

    public void setPickingId(Integer pickingId) {
        this.pickingId = pickingId;
    }

    public PengirimanBarangPartnerData getPartnerData() {
        return partnerData;
    }

    public void setPartnerData(PengirimanBarangPartnerData partnerData) {
        this.partnerData = partnerData;
    }

    public List<PengirimanBarangItem> getListBarang() {
        return listBarang;
    }

    public void setListBarang(List<PengirimanBarangItem> listBarang) {
        this.listBarang = listBarang;
    }

}