
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadProductResponse {

    @SerializedName("result")
    @Expose
    private LoadProductResult result;

    public LoadProductResult getResult() {
        return result;
    }

    public void setResult(LoadProductResult result) {
        this.result = result;
    }

}
