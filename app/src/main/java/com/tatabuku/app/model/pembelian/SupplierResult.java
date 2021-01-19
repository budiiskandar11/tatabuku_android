package com.tatabuku.app.model.pembelian;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierResult implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("street")
    @Expose
    private String street = "";
    @SerializedName("street2")
    @Expose
    private String street2 = "";
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("status_pajak")
    @Expose
    private String statusPajak;
    @SerializedName("purchase_order_total")
    @Expose
    private Double purchaseOrderTotal = 0.0;
    @SerializedName("purchase_order_bulan")
    @Expose
    private Double purchaseOrderBulan = 0.0;
    @SerializedName("purchase_order_hari")
    @Expose
    private Double purchaseOrderHari = 0.0;
    @SerializedName("hutang_total")
    @Expose
    private Double hutangTotal = 0.0;
    @SerializedName("hutang_tahun")
    @Expose
    private Double hutangTahun = 0.0;
    @SerializedName("hutang_bulan")
    @Expose
    private Double hutangBulan = 0.0;
    @SerializedName("hutang_hari")
    @Expose
    private Double hutangHari = 0.0;
    @SerializedName("payment_total")
    @Expose
    private Double paymentTotal = 0.0;
    @SerializedName("payment_tahun")
    @Expose
    private Double paymentTahun = 0.0;
    @SerializedName("payment_bulan")
    @Expose
    private Double paymentBulan = 0.0;
    @SerializedName("payment_hari")
    @Expose
    private Double paymentHari = 0.0;
    @SerializedName("dp_total")
    @Expose
    private Double dpTotal = 0.0;
    @SerializedName("dp_total_bulan")
    @Expose
    private Double dpTotalBulan = 0.0;
    @SerializedName("dp_total_hari")
    @Expose
    private Double dpTotalHari = 0.0;
    @SerializedName("debit_limit")
    @Expose
    private Double debitLimit = 0.0;
    @SerializedName("debit")
    @Expose
    private Double debit = 0.0;
    @SerializedName("dp_pembelian_masuk")
    @Expose
    private Double dpPembelianMasuk = 0.0;
    @SerializedName("dp_pembelian_keluar")
    @Expose
    private Double dpPembelianKeluar = 0.0;
    @SerializedName("image")
    @Expose
    private String image;

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

    public String getPhone() {
        return mobile;
    }

    public void setPhone(String mobile) {
        this.mobile = mobile;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getPurchaseOrderTotal() {
        return purchaseOrderTotal;
    }

    public void setPurchaseOrderTotal(Double purchaseOrderTotal) {
        this.purchaseOrderTotal = purchaseOrderTotal;
    }

    public Double getPurchaseOrderBulan() {
        return purchaseOrderBulan;
    }

    public void setPurchaseOrderBulan(Double purchaseOrderBulan) {
        this.purchaseOrderBulan = purchaseOrderBulan;
    }

    public Double getPurchaseOrderHari() {
        return purchaseOrderHari;
    }

    public void setPurchaseOrderHari(Double purchaseOrderHari) {
        this.purchaseOrderHari = purchaseOrderHari;
    }

    public Double getHutangTotal() {
        return hutangTotal;
    }

    public void setHutangTotal(Double hutangTotal) {
        this.hutangTotal = hutangTotal;
    }

    public Double getHutangBulan() {
        return hutangBulan;
    }

    public void setHutangBulan(Double hutangBulan) {
        this.hutangBulan = hutangBulan;
    }

    public Double getHutangHari() {
        return hutangHari;
    }

    public void setHutangHari(Double hutangHari) {
        this.hutangHari = hutangHari;
    }

    public Double getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(Double paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public Double getPaymentBulan() {
        return paymentBulan;
    }

    public void setPaymentBulan(Double paymentBulan) {
        this.paymentBulan = paymentBulan;
    }

    public Double getPaymentHari() {
        return paymentHari;
    }

    public void setPaymentHari(Double paymentHari) {
        this.paymentHari = paymentHari;
    }

    public Double getDpTotal() {
        return dpTotal;
    }

    public void setDpTotal(Double dpTotal) {
        this.dpTotal = dpTotal;
    }

    public Double getDpTotalBulan() {
        return dpTotalBulan;
    }

    public void setDpTotalBulan(Double dpTotalBulan) {
        this.dpTotalBulan = dpTotalBulan;
    }

    public Double getDpTotalHari() {
        return dpTotalHari;
    }

    public void setDpTotalHari(Double dpTotalHari) {
        this.dpTotalHari = dpTotalHari;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getDebitLimit() {
        return debitLimit;
    }

    public void setDebitLimit(Double debitLimit) {
        this.debitLimit = debitLimit;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getDpPembelianMasuk() {
        return dpPembelianMasuk;
    }

    public void setDpPembelianMasuk(Double dpPembelianMasuk) {
        this.dpPembelianMasuk = dpPembelianMasuk;
    }

    public Double getDpPembelianKeluar() {
        return dpPembelianKeluar;
    }

    public void setDpPembelianKeluar(Double dpPembelianKeluar) {
        this.dpPembelianKeluar = dpPembelianKeluar;
    }

    public String getStatusPajak() {
        return statusPajak;
    }

    public void setStatusPajak(String statusPajak) {
        this.statusPajak = statusPajak;
    }

    public Double getHutangTahun() {
        return hutangTahun;
    }

    public void setHutangTahun(Double hutangTahun) {
        this.hutangTahun = hutangTahun;
    }

    public Double getPaymentTahun() {
        return paymentTahun;
    }

    public void setPaymentTahun(Double paymentTahun) {
        this.paymentTahun = paymentTahun;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.street);
        dest.writeString(this.street2);
        dest.writeString(this.city);
        dest.writeString(this.statusPajak);
        dest.writeValue(this.purchaseOrderTotal);
        dest.writeValue(this.purchaseOrderBulan);
        dest.writeValue(this.purchaseOrderHari);
        dest.writeValue(this.hutangTotal);
        dest.writeValue(this.hutangTahun);
        dest.writeValue(this.hutangBulan);
        dest.writeValue(this.hutangHari);
        dest.writeValue(this.paymentTotal);
        dest.writeValue(this.paymentTahun);
        dest.writeValue(this.paymentBulan);
        dest.writeValue(this.paymentHari);
        dest.writeValue(this.dpTotal);
        dest.writeValue(this.dpTotalBulan);
        dest.writeValue(this.dpTotalHari);
        dest.writeValue(this.debitLimit);
        dest.writeValue(this.debit);
        dest.writeValue(this.dpPembelianMasuk);
        dest.writeValue(this.dpPembelianKeluar);
        dest.writeString(this.image);
    }

    public SupplierResult() {
    }

    protected SupplierResult(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.mobile = in.readString();
        this.street = in.readString();
        this.street2 = in.readString();
        this.city = in.readString();
        this.statusPajak = in.readString();
        this.purchaseOrderTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.purchaseOrderBulan = (Double) in.readValue(Double.class.getClassLoader());
        this.purchaseOrderHari = (Double) in.readValue(Double.class.getClassLoader());
        this.hutangTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.hutangTahun = (Double) in.readValue(Double.class.getClassLoader());
        this.hutangBulan = (Double) in.readValue(Double.class.getClassLoader());
        this.hutangHari = (Double) in.readValue(Double.class.getClassLoader());
        this.paymentTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.paymentTahun = (Double) in.readValue(Double.class.getClassLoader());
        this.paymentBulan = (Double) in.readValue(Double.class.getClassLoader());
        this.paymentHari = (Double) in.readValue(Double.class.getClassLoader());
        this.dpTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.dpTotalBulan = (Double) in.readValue(Double.class.getClassLoader());
        this.dpTotalHari = (Double) in.readValue(Double.class.getClassLoader());
        this.debitLimit = (Double) in.readValue(Double.class.getClassLoader());
        this.debit = (Double) in.readValue(Double.class.getClassLoader());
        this.dpPembelianMasuk = (Double) in.readValue(Double.class.getClassLoader());
        this.dpPembelianKeluar = (Double) in.readValue(Double.class.getClassLoader());
        this.image = in.readString();
    }

    public static final Creator<SupplierResult> CREATOR = new Creator<SupplierResult>() {
        @Override
        public SupplierResult createFromParcel(Parcel source) {
            return new SupplierResult(source);
        }

        @Override
        public SupplierResult[] newArray(int size) {
            return new SupplierResult[size];
        }
    };
}
