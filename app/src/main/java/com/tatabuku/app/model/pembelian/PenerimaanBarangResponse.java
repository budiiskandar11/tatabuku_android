
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PenerimaanBarangResponse {

    @SerializedName("result")
    @Expose
    private PenerimaanBarangResult result;

    public PenerimaanBarangResult getResult() {
        return result;
    }

    public void setResult(PenerimaanBarangResult result) {
        this.result = result;
    }

}

