package com.tatabuku.app.model.fixed_asset;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPurchasesResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("purchase_list")
    @Expose
    private List<GetPurchasesModel> purchaseList = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GetPurchasesModel> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<GetPurchasesModel> purchaseList) {
        this.purchaseList = purchaseList;
    }

}