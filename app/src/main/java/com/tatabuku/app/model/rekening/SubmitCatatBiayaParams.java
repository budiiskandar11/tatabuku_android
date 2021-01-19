package com.tatabuku.app.model.rekening;

import java.util.ArrayList;
import java.util.List;

public class SubmitCatatBiayaParams {
    public int bank_id;
    public String date;
    public String type;
    public List<SubmitCatatBiayaList> list_biaya;

    public SubmitCatatBiayaParams(int bank_id, String date, List<SubmitCatatBiayaList> list_biaya) {
        this.bank_id = bank_id;
        this.date = date;
        this.list_biaya = list_biaya;
        this.type = "biaya";
    }

    public SubmitCatatBiayaParams(int bank_id, String date, String type, List<SubmitCatatBiayaList> list_biaya) {
        this.bank_id = bank_id;
        this.date = date;
        this.type = type;
        this.list_biaya = list_biaya;
    }
}
