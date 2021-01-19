package com.tatabuku.app.model.pembelian;


// {"jsonrpc": "2.0", "params": {"db": "Tatabuku","login": "admin", "password": "a"}}
public class LoginParams {
    private String db;
    private  String login;
    private String password;

    public LoginParams(String login, String password) {
        db = "Tatabuku";
        this.login = login;
        this.password = password;
    }

    public LoginParams(String db, String login, String password) {
        this.db = db;
        this.login = login;
        this.password = password;
    }
}
