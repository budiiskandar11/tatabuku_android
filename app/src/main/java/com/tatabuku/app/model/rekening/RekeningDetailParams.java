package com.tatabuku.app.model.rekening;

import com.tatabuku.app.util.StringHelper;

public class RekeningDetailParams {
    public int bank_id;
    public String filter;
    public String name;

    public RekeningDetailParams(int bank_id, String filter, String name) {
        this.bank_id = bank_id;
        this.filter = filter;
        this.name = name;
    }
}
