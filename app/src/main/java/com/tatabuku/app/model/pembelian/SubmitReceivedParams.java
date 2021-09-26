package com.tatabuku.app.model.pembelian;

public class SubmitReceivedParams {
    private ReceivedModel move_lines[];
    private Integer id;

    public SubmitReceivedParams(ReceivedModel[] move_lines, Integer id) {
        this.move_lines = move_lines;
        this.id = id;
    }
}
