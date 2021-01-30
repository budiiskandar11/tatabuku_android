package com.tatabuku.app.model.rekening;

public class CreateBankAccountParams {
    public String name;
    public String account_no;

    public CreateBankAccountParams(String name, String account_no) {
        this.name = name;
        this.account_no = account_no;
    }
}
