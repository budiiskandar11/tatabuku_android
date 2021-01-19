package com.tatabuku.app.model.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HomepageDataResponse {

    @SerializedName("result")
    @Expose
    private HomepageDataResult result;

    public HomepageDataResult getResult() {
        return result;
    }

    public void setResult(HomepageDataResult result) {
        this.result = result;
    }

}

