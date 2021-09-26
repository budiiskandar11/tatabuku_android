package com.tatabuku.app.model.pembelian;

public class RegisterModel {
    private String jsonrpc = "2.0";
    private RegisterParams params;

    public RegisterModel(String store_name, String value_fullname, String value_mobile, String value_email, String value_password) {
        params = new RegisterParams(store_name, value_fullname,value_mobile,value_email,value_password);
    }
}
