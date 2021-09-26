
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProvinceDataResponse {

    @SerializedName("result")
    @Expose
    private List<ProvinceData> result = null;

    public List<ProvinceData> getResult() {
        return result;
    }

    public void setResult(List<ProvinceData> result) {
        this.result = result;
    }

}
