package com.tatabuku.app.model.fixed_asset;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSupplierResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("supplier_list")
    @Expose
    private List<GetSupplierModel> supplierList = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetSupplierModel> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<GetSupplierModel> supplierList) {
        this.supplierList = supplierList;
    }

}
