package com.tatabuku.app.model.rekening;

public class CatatBiayaParams {
    public int bank_id;
    public String name;
    public String type;

    public CatatBiayaParams(int bank_id) {
        this.bank_id = bank_id;
        this.name = "";
    }

    public CatatBiayaParams(int bank_id, String name) {
        this.bank_id = bank_id;
        this.name = name;
        this.type = "biaya";
    }

    public CatatBiayaParams(int bank_id, String name, String type) {
        this.bank_id = bank_id;
        this.name = name;
        this.type = type;
    }
}
