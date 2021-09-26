
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveOrderResult {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Untaxed")
    @Expose
    private Double untaxed;
    @SerializedName("Tax")
    @Expose
    private Double tax;
    @SerializedName("Total")
    @Expose
    private Double total;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Double getUntaxed() {
        return untaxed;
    }

    public void setUntaxed(Double untaxed) {
        this.untaxed = untaxed;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}