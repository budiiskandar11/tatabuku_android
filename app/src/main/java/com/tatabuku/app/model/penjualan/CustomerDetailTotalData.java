package com.tatabuku.app.model.penjualan;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetailTotalData implements Parcelable {

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
    private String street2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("status_pajak")
    @Expose
    private String statusPajak;
    @SerializedName("order_total")
    @Expose
    private Double orderTotal = 0.0;
    @SerializedName("order_bulan")
    @Expose
    private Double orderBulan = 0.0;
    @SerializedName("order_hari")
    @Expose
    private Double orderHari = 0.0;
    @SerializedName("piutang_total")
    @Expose
    private Double piutangTotal = 0.0;
    @SerializedName("piutang_60")
    @Expose
    private Double piutang60 = 0.0;
    @SerializedName("piutang_30")
    @Expose
    private Double piutang30 = 0.0;
    @SerializedName("piutang_hari")
    @Expose
    private Double piutangHari = 0.0;
    @SerializedName("saldo_dp")
    @Expose
    private Double saldoDp = 0.0;
    @SerializedName("dp_masuk")
    @Expose
    private Double dpMasuk = 0.0;
    @SerializedName("dp_keluar")
    @Expose
    private Double dpKeluar = 0.0;

    @SerializedName("payment_total")
    @Expose
    private Double paymentTotal = 0.0;
    @SerializedName("payment_bulan")
    @Expose
    private Double paymentBulan = 0.0;
    @SerializedName("payment_hari")
    @Expose
    private Double paymentHari = 0.0;

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

    public String getStatusPajak() {
        return statusPajak;
    }

    public void setStatusPajak(String statusPajak) {
        this.statusPajak = statusPajak;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Double getOrderBulan() {
        return orderBulan;
    }

    public void setOrderBulan(Double orderBulan) {
        this.orderBulan = orderBulan;
    }

    public Double getOrderHari() {
        return orderHari;
    }

    public void setOrderHari(Double orderHari) {
        this.orderHari = orderHari;
    }

    public Double getPiutangTotal() {
        return piutangTotal;
    }

    public void setPiutangTotal(Double piutangTotal) {
        this.piutangTotal = piutangTotal;
    }

    public Double getPiutang60() {
        return piutang60;
    }

    public void setPiutang60(Double piutang60) {
        this.piutang60 = piutang60;
    }

    public Double getPiutang30() {
        return piutang30;
    }

    public void setPiutang30(Double piutang30) {
        this.piutang30 = piutang30;
    }

    public Double getPiutangHari() {
        return piutangHari;
    }

    public void setPiutangHari(Double piutangHari) {
        this.piutangHari = piutangHari;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.street);
        dest.writeString(this.street2);
        dest.writeString(this.city);
        dest.writeString(this.statusPajak);
        dest.writeValue(this.orderTotal);
        dest.writeValue(this.orderBulan);
        dest.writeValue(this.orderHari);
        dest.writeValue(this.piutangTotal);
        dest.writeValue(this.piutang60);
        dest.writeValue(this.piutang30);
        dest.writeValue(this.piutangHari);
        dest.writeValue(this.saldoDp);
        dest.writeValue(this.dpMasuk);
        dest.writeValue(this.dpKeluar);
        dest.writeValue(this.paymentBulan);
        dest.writeValue(this.paymentHari);
        dest.writeValue(this.paymentTotal);
    }

    public CustomerDetailTotalData() {
    }

    protected CustomerDetailTotalData(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.image = in.readString();
        this.street = in.readString();
        this.street2 = in.readString();
        this.city = in.readString();
        this.statusPajak = in.readString();
        this.orderTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.orderBulan = (Double) in.readValue(Double.class.getClassLoader());
        this.orderHari = (Double) in.readValue(Double.class.getClassLoader());
        this.piutangTotal = (Double) in.readValue(Double.class.getClassLoader());
        this.piutang60 = (Double) in.readValue(Double.class.getClassLoader());
        this.piutang30 = (Double) in.readValue(Double.class.getClassLoader());
        this.piutangHari = (Double) in.readValue(Double.class.getClassLoader());
        this.saldoDp = (Double) in.readValue(Double.class.getClassLoader());
        this.dpMasuk = (Double) in.readValue(Double.class.getClassLoader());
        this.dpKeluar = (Double) in.readValue(Double.class.getClassLoader());
        this.paymentBulan = (Double) in.readValue(Double.class.getClassLoader());
        this.paymentHari = (Double) in.readValue(Double.class.getClassLoader());
        this.paymentTotal = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<CustomerDetailTotalData> CREATOR = new Parcelable.Creator<CustomerDetailTotalData>() {
        @Override
        public CustomerDetailTotalData createFromParcel(Parcel source) {
            return new CustomerDetailTotalData(source);
        }

        @Override
        public CustomerDetailTotalData[] newArray(int size) {
            return new CustomerDetailTotalData[size];
        }
    };
}