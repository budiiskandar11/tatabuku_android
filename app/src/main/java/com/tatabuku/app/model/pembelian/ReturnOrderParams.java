package com.tatabuku.app.model.pembelian;

import com.tatabuku.app.util.StringHelper;

public class ReturnOrderParams {
    private ReturnModel move_lines[];
    private Integer partner_id;
    private String date;

    public ReturnOrderParams(ReturnModel[] move_lines, Integer partner_id) {
        this.move_lines = move_lines;
        this.partner_id = partner_id;
        date = StringHelper.getTodayDate("yyyy-MM-dd");
    }
}
