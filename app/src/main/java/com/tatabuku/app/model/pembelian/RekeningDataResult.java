package com.tatabuku.app.model.pembelian;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RekeningDataResult {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("total_dp")
    @Expose
    private Double totalDp;
    @SerializedName("bank_ids")
    @Expose
    private List<RekeningBank> bankIds = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getTotalDp() {
        return totalDp;
    }

    public void setTotalDp(Double totalDp) {
        this.totalDp = totalDp;
    }

    public List<RekeningBank> getBankIds() {
        return bankIds;
    }

    public void setBankIds(List<RekeningBank> bankIds) {
        this.bankIds = bankIds;
    }

}