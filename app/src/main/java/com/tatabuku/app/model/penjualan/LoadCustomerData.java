package com.tatabuku.app.model.penjualan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.model.pembelian.CityData;
import com.tatabuku.app.model.pembelian.PaymentTerm;
import com.tatabuku.app.model.pembelian.ProvinceData;
import com.tatabuku.app.model.pembelian.SubdistrictData;

public class LoadCustomerData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("street2")
    @Expose
    private String street2;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("credit_limit")
    @Expose
    private Double creditLimit = 0.0;
    @SerializedName("debit_limit")
    @Expose
    private Double debitLimit = 0.0;
    @SerializedName("status_pajak")
    @Expose
    private String statusPajak;
    @SerializedName("vat")
    @Expose
    private String vat;
    @SerializedName("property_payment_term_id")
    @Expose
    private PaymentTerm propertyPaymentTermId;
    @SerializedName("city_id")
    @Expose
    private CityData cityId;
    @SerializedName("subdistrict_id")
    @Expose
    private SubdistrictData subdistrictId;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("state_id")
    @Expose
    private ProvinceData stateId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("customer")
    @Expose
    private Boolean customer;
    @SerializedName("supplier")
    @Expose
    private Boolean supplier;

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

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getDebitLimit() {
        return debitLimit;
    }

    public void setDebitLimit(Double debitLimit) {
        this.debitLimit = debitLimit;
    }

    public String getStatusPajak() {
        return statusPajak;
    }

    public void setStatusPajak(String statusPajak) {
        this.statusPajak = statusPajak;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public PaymentTerm getPropertyPaymentTermId() {
        return propertyPaymentTermId;
    }

    public void setPropertyPaymentTermId(PaymentTerm propertyPaymentTermId) {
        this.propertyPaymentTermId = propertyPaymentTermId;
    }

    public CityData getCityId() {
        return cityId;
    }

    public void setCityId(CityData cityId) {
        this.cityId = cityId;
    }

    public SubdistrictData getSubdistrictId() {
        return subdistrictId;
    }

    public void setSubdistrictId(SubdistrictData subdistrictId) {
        this.subdistrictId = subdistrictId;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public ProvinceData getStateId() {
        return stateId;
    }

    public void setStateId(ProvinceData stateId) {
        this.stateId = stateId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getCustomer() {
        return customer;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
    }

    public Boolean getSupplier() {
        return supplier;
    }

    public void setSupplier(Boolean supplier) {
        this.supplier = supplier;
    }

}