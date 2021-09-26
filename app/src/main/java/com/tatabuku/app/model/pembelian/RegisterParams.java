package com.tatabuku.app.model.pembelian;

public class RegisterParams {
    private String store_name;
    private String value_fullname;
    private String value_mobile;
    private String value_email;
    private String value_password;

    public RegisterParams(String store_name, String value_fullname, String value_mobile, String value_email, String value_password) {
        this.store_name = store_name;
        this.value_fullname = value_fullname;
        this.value_mobile = value_mobile;
        this.value_email = value_email;
        this.value_password = value_password;
    }
}

