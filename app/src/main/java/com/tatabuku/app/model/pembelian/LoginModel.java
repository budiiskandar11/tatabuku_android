package com.tatabuku.app.model.pembelian;

// {"jsonrpc": "2.0", "params": {"db": "Tatabuku","login": "admin", "password": "a"}}
public class LoginModel {
    private String jsonrpc = "2.0";
    private LoginParams params;

    public LoginModel(String login, String password) {
        params = new LoginParams(login, password);
    }

    public LoginModel(String db, String login, String password) {
        params = new LoginParams(db, login, password);
    }
}
