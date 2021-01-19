
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityDataResponse {

    @SerializedName("result")
    @Expose
    private List<CityData> result = null;

    public List<CityData> getResult() {
        return result;
    }

    public void setResult(List<CityData> result) {
        this.result = result;
    }

}

