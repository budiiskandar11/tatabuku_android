package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetURLResult {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tatabuku_url")
    @Expose
    private String tatabukuUrl;
    @SerializedName("tatabuku_db")
    @Expose
    private String tatabukuDb;
    @SerializedName("tatabuku_password")
    @Expose
    private String tatabukuPassword;
    @SerializedName("tatabuku_user")
    @Expose
    private String tatabukuUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTatabukuUrl() {
        return tatabukuUrl;
    }

    public void setTatabukuUrl(String tatabukuUrl) {
        this.tatabukuUrl = tatabukuUrl;
    }

    public String getTatabukuDb() {
        return tatabukuDb;
    }

    public void setTatabukuDb(String tatabukuDb) {
        this.tatabukuDb = tatabukuDb;
    }

    public String getTatabukuPassword() {
        return tatabukuPassword;
    }

    public void setTatabukuPassword(String tatabukuPassword) {
        this.tatabukuPassword = tatabukuPassword;
    }

    public String getTatabukuUser() {
        return tatabukuUser;
    }

    public void setTatabukuUser(String tatabukuUser) {
        this.tatabukuUser = tatabukuUser;
    }

}