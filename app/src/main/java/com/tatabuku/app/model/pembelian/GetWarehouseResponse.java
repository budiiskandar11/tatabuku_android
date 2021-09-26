
package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetWarehouseResponse {

    @SerializedName("result")
    @Expose
    private List<WarehouseAddress> result = null;

    public List<WarehouseAddress> getResult() {
        return result;
    }

    public void setResult(List<WarehouseAddress> result) {
        this.result = result;
    }

}