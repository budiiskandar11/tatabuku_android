package com.tatabuku.app.model.pembelian;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PenerimaanBarangResult {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("Partner_Data")
    @Expose
    private PartnerData partnerData;
    @SerializedName("List Barang")
    @Expose
    private List<PenerimaanBarangItem> listBarang = null;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PartnerData getPartnerData() {
        return partnerData;
    }

    public void setPartnerData(PartnerData partnerData) {
        this.partnerData = partnerData;
    }

    public List<PenerimaanBarangItem> getListBarang() {
        return listBarang;
    }

    public void setListBarang(List<PenerimaanBarangItem> listBarang) {
        this.listBarang = listBarang;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}