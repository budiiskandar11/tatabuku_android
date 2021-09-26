package com.tatabuku.app.model.rekening;

public class CreateBankAccountParams {
    public String name;
    public String account_no;
    public Double saldo_awal;

    public CreateBankAccountParams(String name, String account_no, Double saldo_awal) {
        this.name = name;
        this.account_no = account_no;
        this.saldo_awal = saldo_awal;
    }
}
