package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LunasDataResponse {

    @SerializedName("result")
    @Expose
    private List<LunasDataResult> result = null;

    public List<LunasDataResult> getResult() {
        return result;
    }

    public void setResult(List<LunasDataResult> result) {
        this.result = result;
    }

}
