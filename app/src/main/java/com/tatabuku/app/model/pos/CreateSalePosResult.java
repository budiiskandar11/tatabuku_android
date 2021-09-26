package com.tatabuku.app.model.pos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateSalePosResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("order_id")
    @Expose
    private Integer order_id;
    @SerializedName("invoice_id")
    @Expose
    private Integer invoice_id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(Integer invoice_id) {
        this.invoice_id = invoice_id;
    }

}
