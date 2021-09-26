package com.tatabuku.app.model.rekening;

import java.util.ArrayList;

public class SubmitEditBiayaParams {
    public int voucher_id;
    public ArrayList<SubmitEditBiayaList> list_biaya;

    public SubmitEditBiayaParams(int voucher_id, ArrayList<SubmitEditBiayaList> list_biaya) {
        this.voucher_id = voucher_id;
        this.list_biaya = list_biaya;
    }
}
