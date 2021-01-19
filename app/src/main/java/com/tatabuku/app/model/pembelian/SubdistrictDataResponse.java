package com.tatabuku.app.model.pembelian;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubdistrictDataResponse {

    @SerializedName("result")
    @Expose
    private List<SubdistrictData> result = null;

    public List<SubdistrictData> getResult() {
        return result;
    }

    public void setResult(List<SubdistrictData> result) {
        this.result = result;
    }

}