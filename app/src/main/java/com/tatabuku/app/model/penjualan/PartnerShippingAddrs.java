package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartnerShippingAddrs {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("street2")
    @Expose
    private Boolean street2;
    @SerializedName("kecamatan")
    @Expose
    private Boolean kecamatan;
    @SerializedName("kota")
    @Expose
    private Boolean kota;
    @SerializedName("prov")
    @Expose
    private Boolean prov;
    @SerializedName("zip")
    @Expose
    private Boolean zip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Boolean getStreet2() {
        return street2;
    }

    public void setStreet2(Boolean street2) {
        this.street2 = street2;
    }

    public Boolean getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(Boolean kecamatan) {
        this.kecamatan = kecamatan;
    }

    public Boolean getKota() {
        return kota;
    }

    public void setKota(Boolean kota) {
        this.kota = kota;
    }

    public Boolean getProv() {
        return prov;
    }

    public void setProv(Boolean prov) {
        this.prov = prov;
    }

    public Boolean getZip() {
        return zip;
    }

    public void setZip(Boolean zip) {
        this.zip = zip;
    }

}