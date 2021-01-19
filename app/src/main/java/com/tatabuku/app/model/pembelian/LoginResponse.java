package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
{
    "result": {
        "partner_id": 3,
    }
}
 */
public class LoginResponse {

    @SerializedName("result")
    @Expose
    private LoginResult result;

    public LoginResult getResult() {
        return result;
    }

    public void setResult(LoginResult result) {
        this.result = result;
    }

}