package com.tatabuku.app.model.pembelian;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RekeningDataParams {
    Integer partner_id;

    public RekeningDataParams(Integer partner_id) {
        this.partner_id = partner_id;
    }
}