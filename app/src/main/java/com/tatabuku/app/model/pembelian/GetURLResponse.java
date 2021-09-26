package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
/*
{
    "result": [
        {
            "name": "Administrator",
            "tatabuku_url": "",
            "tatabuku_db": "",
            "tatabuku_password": "",
            "tatabuku_user": ""
        }
    ]
}
 */

public class GetURLResponse {

    @SerializedName("result")
    @Expose
    private List<GetURLResult> result = null;

    public List<GetURLResult> getResult() {
        return result;
    }

    public void setResult(List<GetURLResult> result) {
        this.result = result;
    }

}

