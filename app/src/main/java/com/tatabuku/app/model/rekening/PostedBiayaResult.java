package com.tatabuku.app.model.rekening;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostedBiayaResult {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("header_data")
    @Expose
    private PostedBiayaHeader headerData;
    @SerializedName("list_biaya")
    @Expose
    private List<PostedBiayaList> listBiaya = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PostedBiayaHeader getHeaderData() {
        return headerData;
    }

    public void setHeaderData(PostedBiayaHeader headerData) {
        this.headerData = headerData;
    }

    public List<PostedBiayaList> getListBiaya() {
        return listBiaya;
    }

    public void setListBiaya(List<PostedBiayaList> listBiaya) {
        this.listBiaya = listBiaya;
    }

}