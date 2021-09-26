package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("street2")
    @Expose
    private Boolean street2;
    @SerializedName("city")
    @Expose
    private Boolean city;
    @SerializedName("status_pajak")
    @Expose
    private String statusPajak;
    @SerializedName("phone")
    @Expose
    private String phone = "";
    @SerializedName("total_penjualan_tahun")
    @Expose
    private Double totalPenjualanTahun = 0.0;
    @SerializedName("penjualan_bulan")
    @Expose
    private Double penjualanBulan = 0.0;
    @SerializedName("penjualan_hari")
    @Expose
    private Double penjualanHari = 0.0;
    @SerializedName("payment_hari")
    @Expose
    private Double paymentHari = 0.0;
    @SerializedName("payment_bulan")
    @Expose
    private Double paymentBulan = 0.0;
    @SerializedName("payment_tahun")
    @Expose
    private Double paymentTahun = 0.0;
    @SerializedName("hutang_blm_jatuh_tempo")
    @Expose
    private Double hutangBlmJatuhTempo = 0.0;
    @SerializedName("hutang_30")
    @Expose
    private Double hutang30 = 0.0;
    @SerializedName("hutang_60")
    @Expose
    private Double hutang60 = 0.0;
    @SerializedName("saldo_dp")
    @Expose
    private Double saldoDp = 0.0;
    @SerializedName("dp_masuk")
    @Expose
    private Double dpMasuk = 0.0;
    @SerializedName("dp_keluar")
    @Expose
    private Double dpKeluar = 0.0;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Boolean getCity() {
        return city;
    }

    public void setCity(Boolean city) {
        this.city = city;
    }

    public String getStatusPajak() {
        return statusPajak;
    }

    public void setStatusPajak(String statusPajak) {
        this.statusPajak = statusPajak;
    }

    public Double getTotalPenjualanTahun() {
        return totalPenjualanTahun;
    }

    public void setTotalPenjualanTahun(Double totalPenjualanTahun) {
        this.totalPenjualanTahun = totalPenjualanTahun;
    }

    public Double getPenjualanBulan() {
        return penjualanBulan;
    }

    public void setPenjualanBulan(Double penjualanBulan) {
        this.penjualanBulan = penjualanBulan;
    }

    public Double getPenjualanHari() {
        return penjualanHari;
    }

    public void setPenjualanHari(Double penjualanHari) {
        this.penjualanHari = penjualanHari;
    }

    public Double getPaymentHari() {
        return paymentHari;
    }

    public void setPaymentHari(Double paymentHari) {
        this.paymentHari = paymentHari;
    }

    public Double getPaymentBulan() {
        return paymentBulan;
    }

    public void setPaymentBulan(Double paymentBulan) {
        this.paymentBulan = paymentBulan;
    }

    public Double getPaymentTahun() {
        return paymentTahun;
    }

    public void setPaymentTahun(Double paymentTahun) {
        this.paymentTahun = paymentTahun;
    }

    public Double getHutangBlmJatuhTempo() {
        return hutangBlmJatuhTempo;
    }

    public void setHutangBlmJatuhTempo(Double hutangBlmJatuhTempo) {
        this.hutangBlmJatuhTempo = hutangBlmJatuhTempo;
    }

    public Double getHutang30() {
        return hutang30;
    }

    public void setHutang30(Double hutang30) {
        this.hutang30 = hutang30;
    }

    public Double getHutang60() {
        return hutang60;
    }

    public void setHutang60(Double hutang60) {
        this.hutang60 = hutang60;
    }

    public Double getSaldoDp() {
        return saldoDp;
    }

    public void setSaldoDp(Double saldoDp) {
        this.saldoDp = saldoDp;
    }

    public Double getDpMasuk() {
        return dpMasuk;
    }

    public void setDpMasuk(Double dpMasuk) {
        this.dpMasuk = dpMasuk;
    }

    public Double getDpKeluar() {
        return dpKeluar;
    }

    public void setDpKeluar(Double dpKeluar) {
        this.dpKeluar = dpKeluar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}