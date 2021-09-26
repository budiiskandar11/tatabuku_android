package com.tatabuku.app.model.rekening;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tatabuku.app.util.StringHelper;

public class RekeningDetailLink {

    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("id")
    @Expose
    private String id;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}